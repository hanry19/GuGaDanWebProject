package org.zerock.ex1.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex1.entity.Memo;


import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println("memoRepository = " + memoRepository.getClass().getName());

    }

    @Test
    @DisplayName("insert test")
    public void testInsertDummies() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    @DisplayName("select test1")
    public void testSelect() {
        //db에 존재하는 mno
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println(":::::::::::::::::::::::::::");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println("memo = " + memo);

        }
    }

    @Test
    @Transactional
    @DisplayName("select test2")
    public void testSelect2() {
        //db에 존재하는 mno
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);
        System.out.println(":::::::::::::::::::::::::::");
        System.out.println("memo = " + memo);


    }

    @Test
    @DisplayName("update test ")
    public void testUpdate() {
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
        System.out.println("memoRepository = " + memoRepository.save(memo));
    }

    @Test
    @DisplayName("delete test")
    public void testDelete() {
        Long mno = 100L;

        memoRepository.deleteById(mno);

    }

    @Test
    @DisplayName("paging test1")
    public void testPageDefault() {
        // 1 page 10 post
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println("result = " + result);

    }
    @Test
    @DisplayName("paging test2")
    public void testPageDefault2() {
        // 1 page 10 post
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println("result = " + result);
        System.out.println("=========================");

        // 총 페이지
        System.out.println("total pages = " + result.getTotalPages());
        // 전체 페이지
        System.out.println("total count : " + result.getTotalElements());
        // 현재 페이지 번호
        System.out.println("page number = " + result.getNumber());
        // 페이지당 데이터 개수
        System.out.println("page size = " + result.getSize());
        // 다음 페이지
        System.out.println("has next page? = " + result.hasNext());
        // 시작페이지(0) 여부
        System.out.println("first page? = " + result.isFirst());
    }

    @Test
    @DisplayName("test sorting")
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println("memo = " + memo);
        });

    }
    @Test
    @DisplayName("apply lots of sort")
    public void testSort2(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable = PageRequest.of(0, 10, sortAll);
        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println("memo = " + memo);
        });
    }

    @Test
    @DisplayName("find By Mno Between Order By Mno Desc")
    public void testQueryMethods(){

        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
        for (Memo memo : list) {
            System.out.println("memo = " + memo);
        }
    }

    @Test
    @DisplayName("findByMnoBetween")
    public void testQueryMethodWithPagable() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(memo -> System.out.println("memo = " + memo));

    }

    @Test
    @Transactional
    @Commit
    @DisplayName("delete memo by query method")
    public void testDeleteByQuery() {

        memoRepository.deleteByMnoLessThan(10L);

    }



}