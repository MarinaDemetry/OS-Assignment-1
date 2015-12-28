import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
	
	static String currDirec = "c:/VMW/W/FCI_CU/" ;

	public static void main(String[] args){
		
		String statement , command = null , p1 = null , p2 ;
		
		Scanner in = new Scanner ( System.in ) ; 
		
		while ( true ){
			
			System.out.print ( currDirec + ": " ) ;
			
			statement = in.nextLine() ;
			
			int i=0 ;
			
			if ( statement.equals ( "exit" ) ){
				
				in.close() ;
				break ;
			}
			
			else if ( statement.equals ( "ls" ) )
				ls () ;
				
			else if ( statement.equals ( "pwd" ) )
				pwd () ;
			
			else if ( statement.equals ( "clear" ) )
				clear () ;
			
			else if ( statement.equals ( "help" ) )
				help ();
			
			else if ( statement.equals ( "date" ) )
				date () ;
			
			else {
				
				while ( i<statement.length() ){
				
					if ( statement.charAt(i) == ' ' ){
					
						command = statement.substring( 0 , i ) ;
						break ;
					}	
				
					i ++ ;
				}
			
				if ( command.equals ( "mkdir" ) ){
				
					p1 = statement.substring( i + 1 , statement.length() ) ;
					mkdir ( currDirec + p1 ) ;
				
				}
				
				else if ( command.equals ( "rm" ) ) {
				
					p1 = statement.substring( i + 1 , statement.length() ) ;
					rm ( p1 ) ;
				
				}
			
				else if ( command.equals ( "rmdir" ) ) {
				
					p1 = statement.substring( i + 1 , statement.length() ) ;
					File f = new File ( currDirec + p1 ) ;
					rmdir ( f ) ;
				
				}
				
				else if ( command.equals ( "cat" ) ) {
				
					p1 = statement.substring( i + 1 , statement.length() ) ;
					File f = new File ( currDirec + p1 ) ;
					cat ( f ) ;
				
				}
			
				else if ( command.equals ( "cd" ) ){
				
					p1 = statement.substring( i + 1 , statement.length() ) ;
					cd ( p1 ) ;
				
				}
				
				else if ( command.equals ( "cp" ) ){
					
					int j = ++ i ;
					
					while ( i<statement.length() ){
						
						if ( statement.charAt(i) == ' ' ){
						
							p1 = statement.substring( j , i ) ;
							break ;
						}	
					
						i ++ ;
					}
					
					p2 = statement.substring( ++ i , statement.length() ) ;
					
					cp ( p1 , p2 ) ;
					
				}
				
			}
				
			
		}
		

	}

	private static void help (){
		
		System.out.println ( "args : List all command arguments" + "\n" 
						   + "date :  Current date/time" + "\n" 
						   + "exit  : Stop all" ) ;

	}
	
	private static void date (){
		
		DateFormat dateFormat = new SimpleDateFormat ( "yyyy/MM/dd HH:mm:ss" ) ;
		Date date = new Date() ; 
		System.out.println ( dateFormat.format(date) ) ;
	}
	
	private static void cat ( File f ){
		
		try {
			
			if ( f.exists() ){
			
				FileReader fileReader = new FileReader( f ) ;
				@SuppressWarnings("resource")
				BufferedReader in = new BufferedReader( fileReader ) ;
			
				String line;
			
				while ( (line = in.readLine()) != null ) 
					System.out.println(line);
				
			}
		} 
		
		catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} 
		
		catch (IOException e) {
	
			e.printStackTrace();
		}
		
	}
	
	//@SuppressWarnings("unused")
	private static void pwd (){
	
		System.out.println ( currDirec ) ;
	}
	
	private static void rmdir ( File f ){

		if ( f.isDirectory() ){
			
			if ( f.list().length == 0 )
				f.delete () ;
		
			else {
				
				String arr[] = f.list() ;
				
				for (String string : arr) {

					File fileDelete = new File ( f , string );
					rmdir ( fileDelete ) ;
				}
				
				if ( f.list().length == 0 )
					f.delete () ;
				
			}
		}
		
		else {
			
			f.delete () ;
		}
		
	}
	
	private static void rm ( String file ){
		
		try{
			 
    		File f1 = new File ( currDirec + file );
 
    		if( !f1.delete() ){
    			
    			System.out.println ( "Delete operation is failed." );
    		}
 
    	}
		catch(Exception e){
 
    		e.printStackTrace();
    	}
	}
	
	private static void mkdir ( String dir ){
		
		File f = new File ( dir ) ;
		f.mkdirs() ; 
	}
	
	private static void clear (){
		
		for ( int i=0 ;i<100 ; i++ ){
			
			System.out.println () ;
		}
	}
	
	private static void cp ( String file1 , String file2 ) {
		
		File f1 = new File ( currDirec + file1 ) ;
		File f2 = new File ( currDirec + file2 ) ;
		
		if ( f1.exists() ){
		
			try {
			
				InputStream in = new FileInputStream ( f1 ) ;
				OutputStream out = new FileOutputStream ( f2 ) ;
			
				byte [] buff = new byte[1024] ;
				int len ;
			
				while ( (len = in.read(buff)) != -1 ){
				
					out.write ( buff , 0 , len ) ;
				}
			
				in.close();
				out.close();
			
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		
		else {
			
			System.out.println ( "Error : Files does not exist." ) ;
		}
		
		return ;
	}	
	
	private static void ls(){
		
		File file = null;
	    String[] files;
	            
	    try {      
	         
	         file = new File( currDirec );	                              
	         files = file.list();
	            
	         for ( String path : files ){
	           
	            System.out.println(path);
	         }
	         
	    } catch(Exception e){
	        
	         e.printStackTrace();
	      }
		
	}

	private static void cd ( String newDirec ){

	    File directory = new File ( newDirec ) ;

	    if ( directory.exists() ){
	    	
	    	currDirec = newDirec ;
	    } 
	    else {
	    	
	    	System.out.println( "This Directory does not exist" );
	    }
	    
	}
}
