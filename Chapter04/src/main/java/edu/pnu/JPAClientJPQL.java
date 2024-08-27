package edu.pnu;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import edu.pnu.domain.Board;
import lombok.Builder;

@Builder
public class JPAClientJPQL {

	public static void main(String[] args) {
		// 딱 1번만 생성되는 객체 emf
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter04");
		// DB에 접근할때마다 생성된 후 사라지는 객체 em
		EntityManager em = emf.createEntityManager();
		
		// JPQL로 DB 제어
		try {
			String jpql = "select b from Board b where b.seq > 15 order by b.seq desc";
			TypedQuery<Board> tq = em.createQuery(jpql, Board.class);
			List<Board> list = tq.getResultList();
			
			for (Board b : list) {
				System.out.println(b);
			}
//			String sql = "select b.title, b.content from Board b where b.seq > 15 order by b.seq desc";
//			Query q = em.createQuery(sql);
//			List<Object[]> list2 = q.getResultList();
//			for (Object[] b : list2) {
//				for(Object o : b) {
//					System.out.println(o.toString());
//				}
//			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
