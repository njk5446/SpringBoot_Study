package edu.pnu;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import edu.pnu.domain.Board;
import lombok.Builder;

@Builder
public class JPAClientUpdate {

	public static void main(String[] args) {
		// 딱 1번만 생성되는 객체 emf
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter04");
		// DB에 접근할때마다 생성된 후 사라지는 객체 em
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();		// insert, update, delete시 반드시 필요
		try {
			tx.begin();
			
			long seq = 2L;
			
			System.out.println("수정 전 : "+em.find(Board.class, seq));
			// board에서 seq 값으로 데이터 수정
			Board board = em.find(Board.class, seq);
			board.setTitle("수정된 title"+seq);
			
			tx.commit();
			System.out.println("수정 후 : "+em.find(Board.class, seq));
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
