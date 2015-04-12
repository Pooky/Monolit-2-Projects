package vse.klim05;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Options {

    @Option(name="-m",usage="path to monolit directory")
    private File monolit;

    @Option(name="-o",usage="output to this file")
    private File output;

    @Option(name="-ch",usage="chapter to generate")
    private String chapter;

    // receives other command line parameters than options
    @Argument
    private List arguments = new ArrayList();
	
    public void run(String[] args){
    	
    	
		CmdLineParser parser = new CmdLineParser(this);
		
		try {
		    parser.parseArgument(args);
		} catch( CmdLineException e ) {
		    System.err.println(e.getMessage());
		    System.err.println("java -jar myprogram.jar [options...] arguments...");
		    parser.printUsage(System.err);
		    return;
		}

        if(monolit != null)
            System.out.println("-r flag is set");

        if(output != null)
            System.out.println("-n was ");

        if(chapter != null)
        	System.out.println("-ch is set");


    }
    
}
