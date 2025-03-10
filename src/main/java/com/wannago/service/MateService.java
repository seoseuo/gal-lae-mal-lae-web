package com.wannago.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.wannago.dto.BoardDTO;
import com.wannago.dto.ResponseDTO;
import com.wannago.entity.Board;
import com.wannago.mapper.BoardMapper;
import com.wannago.repository.BoardRepository;

@Service
public class MateService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardMapper boardMapper;
    
    //게시글 작성
    public ResponseDTO createBoard(BoardDTO boardDTO) {
        boardDTO.setBoState(1);
        boardDTO.setBoDate(LocalDateTime.now());
        boardDTO.setBoLike(0);
        if(boardDTO.getLdIdx() ==0){
            boardDTO.setLdIdx(null);
        }
        if(boardDTO.getLsIdx() ==0){
            boardDTO.setLsIdx(null);
        }
        Board board = boardRepository.save(boardMapper.toEntity(boardDTO));
        return new ResponseDTO(true,"게시글 작성 성공");
    }
    
}
