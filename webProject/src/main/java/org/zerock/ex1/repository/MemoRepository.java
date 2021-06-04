package org.zerock.ex1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex1.entity.Memo;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    // Memo객체의 mno값이 70~80사이의 객체들을 구하고 mno의 역순으로 정렬하고 싶다면
    // 메서드 이름 자체가 쿼리문이 되는 이상한 현상
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    // 쿼리 메소드 + pageable 결합!!
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    // 메모 삭제
    void deleteByMnoLessThan(Long aLong);
}
