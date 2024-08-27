package com.rubypaper;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;

@SpringBootTest
public class QueryMethodTest {
	
	@Autowired
	private BoardRepository boardRepo;
	
	// 리스트 출력용 메서드
	List<Board> boardList;
	public void printList(List<Board> list) {
		System.out.println("검색 결과");
		for (Board board : list) 
			System.out.println("--->"+board.toString());
		System.out.println();
	}

// 쿼리 메서드 예제
	
	@Test
	public void testFindByTitle() {	
		// title11과 일치하는 리스트
		boardList = boardRepo.findByTitle("title11");
		printList(boardList);
	}
	@Test
	public void testFindByContent() {
		// content15를 포함하는 리스트
		boardList = boardRepo.findByContentContaining("content15");
		printList(boardList);
	}
	@Test
	public void testFindByTitleOrContent() {
		// title15 또는 content99를 포함하는 리스트
		boardList = boardRepo.findByTitleContainingOrContentContaining("title15","content99");
		printList(boardList);
	}
	@Test
	public void testFindByTitleWithPaing() {
		// title을 포함하는 리스트를 해당하는 페이지 내에서 출력
		// (0, 5) => 0번째 페이지에서, 5개, seq 기준 내림차순 정렬
		Pageable paging = PageRequest.of(0, 5, Sort.Direction.DESC, "seq");
		boardList = boardRepo.findByTitleContaining("title", paging);
		printList(boardList);
	}
	@Test
	public void queryAnnotationTest() {
		// @Query 어노테이션을 이용한 쿼리문 출력
		// title10을 포함하는 리스트
		boardList = boardRepo.queryAnnotationfindByTitle("title10");
		printList(boardList);
	}
//	@Test
//	public void queryAnnotationTest2() {
//		// @Query 어노테이션을 이용한 쿼리문 출력
//		// title10을 포함하는 특정 테이블만 조회
//		List<Object[]> boardlist = boardRepo.queryAnnotationfindBySeqTitleWriterDate("title10");
//		
//		System.out.println("검색 결과");
//		for (Object[] row : boardlist) 
//			System.out.println("--->"+Arrays.toString(row));
//		System.out.println();
//	}
}