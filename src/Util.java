import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;


public class Util {



	public void cloneBaseProject(String destino) throws InvalidRemoteException, TransportException, GitAPIException {
		
		Git.cloneRepository()
		  .setURI("https://github.com/siertech/testeapi.git")
		  .setDirectory(new File(destino))
		  .call();
	}
	
	
	public StringBuffer lerArquivo (String path)
	{

		StringBuffer content = new StringBuffer ();
		BufferedReader buffR = null;
		try
		{
			String texto;
			buffR = new BufferedReader ( new InputStreamReader(
					new FileInputStream(path), "UTF8"));

			while ((texto = buffR.readLine ()) != null)
			{


				content.append(texto+"\n");
			}

		} catch (FileNotFoundException ex)
		{
			System.out.println ("Arquivo n�o encontrado: "+path);
		} catch (IOException er)
		{
			System.out.println ("N�o foi possivel ler o arquivo!");
		}
		finally {

			try {
				buffR.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return content;
	}

	public  void salvarArquivo(String path, String content) throws IOException {

		BufferedWriter buffWrite = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));

		for(String l : content.split("\n")){
			buffWrite.append(l);
			buffWrite.newLine();
		}

		buffWrite.close();
	}

	public void saveConfig(String pathProject, String moduloAngular) throws IOException{

		String content = pathProject+","+moduloAngular;
		
		new File("C://siertech/entity-creator/").mkdirs();

		salvarArquivo("C://siertech/entity-creator/config.dat", content);


	}

	public Config lerConfig() throws IOException{

      
		Config  conf = new Config();
		String[] confs = lerArquivo("C://siertech/entity-creator/config.dat").toString().split(",");
		String pathProject = "";
		String moduloAngular = "";
		
		if(confs.length>0 )
			pathProject = confs[0];
		
		if(confs.length>1 )
			moduloAngular = confs[1];
		
		pathProject = pathProject.trim();
		moduloAngular = moduloAngular.trim();
		
		conf.setModuloAngular(moduloAngular);
		conf.setPathProject(pathProject);
		//StringBuffer lerArquivo (String path);

		return conf;

	}

}
