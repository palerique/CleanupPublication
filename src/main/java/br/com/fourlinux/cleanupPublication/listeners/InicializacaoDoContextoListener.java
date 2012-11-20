package br.com.fourlinux.cleanupPublication.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InicializacaoDoContextoListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("***************************************************************************************");
		System.out.println("***************************************************************************************");
		System.out.println("**********************************CleaunpPublication***********************************");
		System.out.println("*******************************For eXo Platform 3.0.10*********************************");
		System.out.println("**************************************by 4Linux****************************************");
		System.out.println("***************************************************************************************");
		System.out.println("*************************************************************************************PH");
	}

	public void contextDestroyed(ServletContextEvent arg0) {
	}

}
