package br.com.devmedia.wsjwt.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf;

	public static EntityManager getEntityManager() {
		 if (emf == null) {
			emf = Persistence.createEntityManagerFactory("imoveis");
		 }
		 return emf.createEntityManager();
	}
	
}
