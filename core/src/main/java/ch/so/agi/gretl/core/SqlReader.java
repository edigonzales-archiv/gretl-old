package ch.so.agi.gretl.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by bjsvwsch on 10.05.17.
 */
public class SqlReader {

    public static void executeSqlScript(Connection conn, java.io.InputStreamReader script)
    {
        java.io.PushbackReader reader=null;
        reader = new java.io.PushbackReader(script);
        try{
            String line = readSqlStmt(reader);
            while(line!=null){
                // exec sql
                line=line.trim();
                if(line.length()>0){
                    Statement dbstmt = null;
                    try{
                        try{
                            dbstmt = conn.createStatement();
                            Logger.log(Logger.DEBUG_LEVEL, line);
                            dbstmt.execute(line);
                        }finally{
                            dbstmt.close();
                        }
                    }catch(SQLException ex){
                        throw new IllegalStateException(ex);
                    }

                }
                // read next line
                line=readSqlStmt(reader);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }finally{
            try {
                reader.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public static String readSqlStmt(java.io.PushbackReader reader)
            throws IOException {
        StringBuffer stmt=new StringBuffer();
        int c=reader.read();
        while(c!=-1){
            if(c=='-'){
                c=reader.read();
                if(c==-1){
                    stmt.append('-');
                    break;
                }else if(c=='-'){
                    c=reader.read();
                    while(c!=-1){
                        if(c=='\n'){
                            c=reader.read();
                            if(c!=-1 && c!='\r'){
                                reader.unread(c);
                            }
                            break;
                        }else if(c=='\r'){
                            c=reader.read();
                            if(c!=-1 && c!='\n'){
                                reader.unread(c);
                            }
                            break;
                        }
                        c=reader.read();
                    }
                }else{
                    stmt.append('-');
                    stmt.append((char)c);
                }
            }else if(c=='\''){
                stmt.append((char)c);
                while(true){
                    c=reader.read();
                    if(c==-1){
                        break;
                    }else if(c=='\''){
                        c=reader.read();
                        if(c==-1){
                            // eof
                            break;
                        }else if(c=='\''){
                            stmt.append('\'');
                            stmt.append('\'');
                        }else{
                            reader.unread(c);
                            break;
                        }
                    }else{
                        stmt.append((char)c);
                    }
                }
                stmt.append('\'');
            }else if(c==';'){
                stmt.append((char)c);
                // skip end of line
                c=reader.read();
                if(c=='\n'){
                    c=reader.read();
                    if(c!=-1 && c!='\r'){
                        reader.unread(c);
                    }
                }else if(c=='\r'){
                    c=reader.read();
                    if(c!=-1 && c!='\n'){
                        reader.unread(c);
                    }
                }else{
                    if(c!=-1){
                        reader.unread(c);

                    }
                }
                break;
            }else if(c=='\n'){
                c=reader.read();
                if(c!=-1 && c!='\r'){
                    reader.unread(c);
                }
            }else if(c=='\r'){
                c=reader.read();
                if(c!=-1 && c!='\n'){
                    reader.unread(c);
                }
            }else{
                stmt.append((char)c);
            }
            c=reader.read();
        }
        if(stmt.length()==0){
            return null;
        }
        return stmt.toString();
    }

}
