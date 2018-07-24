import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Creator {


	String moduloAngular;
	String pathWeb;
	String pathJava;
	String nameHibernateFile;
	String pathHibernate;
	String nameSpringFile;
	String pathSpring;
	String nameIndexHtmlFile;
	String pathIndexHtmlFile;
	Util util    =  new Util();


	public static void main(String[] args) throws IOException {

	}

	public  void define(String pathProject, String moduloAngular){

		this.moduloAngular = moduloAngular;
		pathWeb = pathProject  + "WebContent\\dev\\";
		pathJava = pathProject + "src\\main\\java\\";
		nameHibernateFile = "hibernate.cfg.xml";
		pathHibernate = pathProject + "src\\main\\resources\\" + nameHibernateFile;
		nameSpringFile = "springapp-servlet.xml";
		pathSpring = pathProject + "WebContent\\WEB-INF\\" + nameSpringFile;
		nameIndexHtmlFile = "index.html";
		pathIndexHtmlFile = pathWeb  + nameIndexHtmlFile;

	}


	public void criarJava(String nomeEntidade, String principalAttr) throws IOException{
		
		
		String basePath ="F:\\work\\StApi\\src\\main\\java\\com\\siertech\\stapi\\cliente\\Cliente";

		String contentEntidade = util.lerArquivo(basePath+".java").toString();
		String contentEntidadeControl = util.lerArquivo(basePath+"Control.java").toString();
		String contentEntidadeService = util.lerArquivo(basePath+"Service.java").toString();
		String contentEntidadeDAO = util.lerArquivo(basePath+"DAO.java").toString();

		contentEntidade = replaceNames(contentEntidade, nomeEntidade);
		//Atributo principal da entidade do tipo String
		contentEntidade = contentEntidade.replaceAll("principalAttr", principalAttr);
		contentEntidadeControl = replaceNames(contentEntidadeControl, nomeEntidade);
		contentEntidadeService = replaceNames(contentEntidadeService, nomeEntidade);
		contentEntidadeDAO = replaceNames(contentEntidadeDAO, nomeEntidade);

		String pathJavaContentForEntidade = GetPathJavaContentForEntidade(nomeEntidade);
		new File(pathJavaContentForEntidade).mkdirs();
		util.salvarArquivo(pathJavaContentForEntidade +nomeEntidade+".java",contentEntidade);
		util.salvarArquivo(pathJavaContentForEntidade+nomeEntidade+"Control.java",contentEntidadeControl);
		util.salvarArquivo(pathJavaContentForEntidade +nomeEntidade+"Service.java",contentEntidadeService);
		util.salvarArquivo(pathJavaContentForEntidade +nomeEntidade+"DAO.java",contentEntidadeDAO);

		modifyHibernateFile(nomeEntidade);
		modifySpringFile(nomeEntidade);
	}

	public String GetPathJavaContentForEntidade(String nomeEntidade){

		return pathJava + nomeEntidade.toLowerCase()+"\\"; 
	}

	public void criarWeb(String nomeEntidade, String menuLabel, String principalAttr, String principalLabel, String principalIcon) throws IOException{

		String pathBase = "F:\\work\\StApi\\WebContent\\dev\\app\\cliente\\";
		String pathBaseHtml = pathBase+"\\html\\";
		String pathBaseJS= pathBase+"\\js\\";

		//HTML
		String detalheHtml= util.lerArquivo(pathBaseHtml+ "clienteDetalhe.html").toString();
		String formHtml= util.lerArquivo(pathBaseHtml+ "clienteForm.html").toString();
		String listHtml = util.lerArquivo(pathBaseHtml+ "clienteList.html").toString();
		String tableView = util.lerArquivo(pathBaseHtml+ "clienteTableView.html").toString();
		String gridView = util.lerArquivo(pathBaseHtml+ "clienteGridView.html").toString();


		//JS
		String controller = util.lerArquivo(pathBaseJS+ "clienteController.js").toString();
		String directive = util.lerArquivo(pathBaseJS+ "clienteDirective.js").toString();
		String factory = util.lerArquivo(pathBaseJS+ "clienteFactory.js").toString();
		String route = util.lerArquivo(pathBaseJS+ "clienteRoute.js").toString();


		formHtml = replaceNames(formHtml, nomeEntidade, menuLabel, principalAttr, principalLabel, principalIcon);
		detalheHtml = replaceNames(detalheHtml,  nomeEntidade, menuLabel, principalAttr, principalLabel, principalIcon);
		listHtml= replaceNames(listHtml,  nomeEntidade, menuLabel, principalAttr, principalLabel, principalIcon);
		gridView = replaceNames(gridView,  nomeEntidade, menuLabel, principalAttr, principalLabel, principalIcon);
		tableView = replaceNames(tableView,  nomeEntidade, menuLabel, principalAttr, principalLabel, principalIcon);

		controller = replaceNames(controller,  nomeEntidade, menuLabel, principalAttr, principalLabel, principalIcon);
		directive = replaceNames(directive,  nomeEntidade, menuLabel, principalAttr, principalLabel, principalIcon);
		factory = replaceNames(factory,  nomeEntidade, menuLabel, principalAttr, principalLabel, principalIcon);
		route = replaceNames(route,  nomeEntidade, menuLabel, principalAttr, principalLabel, principalIcon);


		String pathWebContentForEntidade = GetPathWebContentForEntidade(nomeEntidade);
		new File(pathWebContentForEntidade).mkdirs();
		new File(pathWebContentForEntidade+"html/").mkdirs();
		new File(pathWebContentForEntidade+"js/").mkdirs();
		new File(pathWebContentForEntidade+"css/").mkdirs();
		
		String entidadeLower = nomeEntidade.toLowerCase();

		//html
		util.salvarArquivo(pathWebContentForEntidade +"html/"+entidadeLower+"Detalhe.html",detalheHtml);
		util.salvarArquivo(pathWebContentForEntidade +"html/"+entidadeLower+"List.html",listHtml);
		util.salvarArquivo(pathWebContentForEntidade +"html/"+entidadeLower+"Form.html",formHtml);
		util.salvarArquivo(pathWebContentForEntidade +"html/"+entidadeLower+"TableView.html",tableView);
		util.salvarArquivo(pathWebContentForEntidade +"html/"+entidadeLower+"GridView.html",gridView);
		
		//js
		util.salvarArquivo(pathWebContentForEntidade +"js/"+entidadeLower+"Directive.js",directive);
		util.salvarArquivo(pathWebContentForEntidade +"js/"+entidadeLower+"Factory.js",factory);
		util.salvarArquivo(pathWebContentForEntidade+"js/"+entidadeLower+"Controller.js",controller);
		util.salvarArquivo(pathWebContentForEntidade+"js/"+entidadeLower+"Route.js",route);

		util.salvarArquivo(pathWebContentForEntidade +"css/style.css","/*-- "+nomeEntidade+" CSS --*/");

		modifyIndexHtmlFile(nomeEntidade);
	}
	
	
	public void criarArquivoTesteUnitarioFrontEnd(String nomeEntidade) throws IOException{

		String pathBase = "F:\\work\\StApi\\WebContent\\dev\\app\\cliente\\";
		String specJS = util.lerArquivo(pathBase+ "clienteSpec.js").toString();
	    specJS = replaceNames(specJS, nomeEntidade);
		String pathWebContentForEntidade = GetPathWebContentForEntidade(nomeEntidade);
		String entidadeLower = nomeEntidade.toLowerCase();
		util.salvarArquivo(pathWebContentForEntidade +"/"+entidadeLower+"Spec.js", specJS);
	
	}

	public String GetPathWebContentForEntidade(String nomeEntidade){

		return  pathWeb+"app/" + nomeEntidade.toLowerCase()+"/";
	}

	public void modifyIndexHtmlFile(String nomeEntidade) throws IOException{

		String indexHtml =  util.lerArquivo(pathIndexHtmlFile).toString();

		String marcadorJS = "<!-- build:js js/app.js -->";
		String pathWebJS =  "app/"+nomeEntidade.toLowerCase()+"/js/";
		String declarationJSContent =  getIndexJSDeclaration(nomeEntidade);

		indexHtml = indexHtml.replace(marcadorJS,marcadorJS+ "\n\n" +declarationJSContent);

		String marcadorCSS = "<!-- build:css css/app.css -->";

		String declarationCSSContent = getIndexCSSDeclaration(nomeEntidade);

		indexHtml = indexHtml.replace(marcadorCSS,marcadorCSS+ "\n\n" +declarationCSSContent);


		util.salvarArquivo(pathIndexHtmlFile ,indexHtml);

	}

	public String getIndexJSDeclaration(String nomeEntidade) {

		String pathWebJS =  "app/"+nomeEntidade.toLowerCase()+"/js/";
		String declaracaoBaseJS   = "<script src='" +pathWebJS+ "_file_'></script>";
		String declarationJSContent = 
				"<!-- " +nomeEntidade + " -->\n "
						+declaracaoBaseJS.replace("_file_",nomeEntidade.toLowerCase()+"Controller.js")+ "\n"
						+declaracaoBaseJS.replace("_file_",nomeEntidade.toLowerCase()+"Directive.js")+ "\n"
						+declaracaoBaseJS.replace("_file_",nomeEntidade.toLowerCase()+"Factory.js")+ "\n"
						+declaracaoBaseJS.replace("_file_",nomeEntidade.toLowerCase()+"Route.js")+ "\n";
		return declarationJSContent;
	}

	public String getIndexCSSDeclaration(String nomeEntidade) {

		String pathWebCSS =  "app/"+nomeEntidade.toLowerCase()+"/css/";
		String declaracaoBaseCSS   = "<link href='" +pathWebCSS+ "_file_' />";
		String declarationCSSContent = declaracaoBaseCSS.replace("_file_","style.css");
		return declarationCSSContent;
	}


	public void modifyHibernateFile(String nomeEntidade) throws IOException{

		String hibernate  =  util.lerArquivo(pathHibernate).toString();
		String mappingString = getHibernateDeclaration(nomeEntidade);
		hibernate = hibernate.replace("</session-factory>", "\t"+mappingString+"\n</session-factory>");
		System.out.println("Hibernate content: ");
		System.out.println(hibernate);
		util.salvarArquivo(pathHibernate ,hibernate);

	}

	public String getHibernateDeclaration(String nomeEntidade) {

		return "<mapping class=\""+ nomeEntidade.toLowerCase()  +"." +nomeEntidade+"\" />";
	}

	public void modifySpringFile(String nomeEntidade) throws IOException{

		String spring  =  util.lerArquivo(pathSpring).toString();
		String mappingString =getSpringDeclaration(nomeEntidade);
		spring = spring.replace("<!-- Mapeamento de classes da aplicacao-->", "<!-- Mapeamento de classes da aplicacao--> \n\t" +mappingString);
		System.out.println("Spring content: ");
		System.out.println(spring);
		util.salvarArquivo(pathSpring ,spring);

	}

	public String getSpringDeclaration(String nomeEntidade) {

		return "<context:component-scan base-package=\"" + nomeEntidade.toLowerCase() + "\" />  ";
	}

	public String replaceNames(String content, String nomeEntidade){
		
	
		
		content = content.replaceAll("package com.siertech.stapi.cliente", "package "+nomeEntidade.toLowerCase());
		content = content.replaceAll("cliente", nomeEntidade.toLowerCase());
		content = content.replaceAll("Cliente", nomeEntidade);
		content = content.replaceAll("module(\"stapi\")", "module(\""+this.moduloAngular+"\")");
		content = content.replaceAll("module(\'stapi\')", "module(\""+this.moduloAngular+"\")");
		
		return content;
		
		
	}
	
   public String replaceNames(String content, String nomeEntidade, String menuLabel, String principalAttr, String principalLabel, String principalIcon){
		
	    content = replaceNames(content, nomeEntidade);
		content = content.replaceAll("menulLabel", menuLabel);
		content = content.replaceAll("principalAttr", principalAttr);
		content = content.replaceAll("principalLabel", principalLabel);
		content = content.replaceAll("principalIcon", principalIcon);
		return content;
		
	}
	
	
	


	public void deleteEntidade(String nomeEntidade) throws IOException {

		//index.html
		String indexHtml =  util.lerArquivo(pathIndexHtmlFile).toString();
		indexHtml = indexHtml.replaceAll(getIndexJSDeclaration(nomeEntidade),"");
		indexHtml = indexHtml.replaceAll(getIndexCSSDeclaration(nomeEntidade),"");
		util.salvarArquivo(pathIndexHtmlFile ,indexHtml);

		//Spring
		String spring  =  util.lerArquivo(pathSpring).toString();
		spring = spring.replaceAll(getSpringDeclaration(nomeEntidade),"");
		util.salvarArquivo(pathSpring ,spring);

		//Hibernate
		String hibernate  =  util.lerArquivo(pathHibernate).toString();
		hibernate = hibernate.replaceAll(getHibernateDeclaration(nomeEntidade),"");
		util.salvarArquivo(pathHibernate ,hibernate);


		//Path Web
		String pathWeb = GetPathWebContentForEntidade(nomeEntidade);
		FileUtils.deleteDirectory(new File(pathWeb));

		//Path Java
		String pathJava = GetPathJavaContentForEntidade(nomeEntidade);
		FileUtils.deleteDirectory(new File(pathJava));
	}




}
