package com.wannago.service;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.wannago.dto.BoardDTO;
import com.wannago.dto.BoardSearchDTO;
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
        
        Board board = boardRepository.save(boardMapper.toEntity(boardDTO).setTempUser(boardDTO.getUsIdx()));

        return new ResponseDTO(true,"게시글 작성 성공");
    }
    
    public Page<BoardDTO> getBoardList(BoardSearchDTO boardSearchDTO, Pageable pageable){
        Page<Board> boards = boardRepository.findByFilter(boardSearchDTO, pageable);
        Page<BoardDTO> boardDTOs = boards.map(boardMapper::toDTO);
        return boardDTOs;
    }

    public BoardDTO getBoard(int boIdx){
        Optional<Board> board = boardRepository.findByBoIdx(boIdx);
        if(board.isPresent()){
            return boardMapper.toDTO(board.get());
        }else{
            return null;
        }
    }

    public int likeBoard(int boIdx){
        Optional<Board> board = boardRepository.findByBoIdx(boIdx);
        if(board.isPresent()){
            BoardDTO boardDTO = boardMapper.toDTO(board.get());
            boardDTO.setBoLike(boardDTO.getBoLike() + 1);
            boardRepository.save(boardMapper.toEntity(boardDTO));
            return boardDTO.getBoLike();
        }else{
            return 0;
        }
    }
}
