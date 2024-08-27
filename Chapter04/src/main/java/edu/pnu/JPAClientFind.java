package edu.pnu;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.pnu.domain.Board;

// DB를 조회하는(Select) 클래스
// em.find 사용
public class JPAClientFind {

	public static void main(String[] args) {
		// 딱 1번만 생성되는 객체 emf
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter04");
		// DB에 접근할때마다 생성된 후 사라지는 객체 em
		EntityManager em = emf.createEntityManager();
		try {
			Board searchBoard = em.find(Board.class, 1L); // 리턴타입, ID값(primary key)
			System.out.println("--->" + searchBoard);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
	}
}
