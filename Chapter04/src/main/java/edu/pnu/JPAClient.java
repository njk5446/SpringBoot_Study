package edu.pnu;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import edu.pnu.domain.Board;
import lombok.Builder;

@Builder
public class JPAClient {

	public static void main(String[] args) {
		// 딱 1번만 생성되는 객체 emf
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter04");
		// DB에 접근할때마다 생성된 후 사라지는 객체 em
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();		// insert, update, delete시 반드시 필요
		try {
			tx.begin();
			
			// board에 데이터 추가			
			for (int i =0; i<10; i++) {
			em.persist(Board.builder()	// persist = save
					.title("JPA 제목" + i)
					.writer("관리자"+ i)
					.content("JPA 글 등록"+i)
					.createDate(new Date())
					.cnt(0L)
					.build());
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
