package edu.pnu;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import edu.pnu.domain.Board;
import lombok.Builder;

@Builder
public class JPAClientDelete {

	public static void main(String[] args) {
		// 딱 1번만 생성되는 객체 emf
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter04");
		// DB에 접근할때마다 생성된 후 사라지는 객체 em
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();		// insert, update, delete시 반드시 필요
		try {
			tx.begin();
			
			// board에서 데이터 삭제
			for (int i =5; i<10; i++) {
				Board board2 = em.find(Board.class, (long) i);
				board2.setSeq((long) i);
				
				em.remove(board2);
			}
			
			tx.commit();
		}catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		finally {
			em.close();
			emf.close();
		}
	}
}
