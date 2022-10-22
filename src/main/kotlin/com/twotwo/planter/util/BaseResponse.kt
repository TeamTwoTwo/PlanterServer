package com.twotwo.planter.util

import io.lettuce.core.dynamic.Commands
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody


fun move(
    @PathVariable name: String?,
    @RequestBody moveDto: MoveDto
): ResponseEntity<MoveResponseDto?>? {
    val headers = HttpHeaders()
    headers.set("Game", "Chess")
    val command: String = makeMoveCmd(moveDto.getSource(), moveDto.getTarget())
    springChessService.move(name, command, Commands(command))
    val moveResponseDto = MoveResponseDto(
        springChessService
            .continuedGameInfo(name), name
    )
    return ResponseEntity<MoveResponseDto>(
        moveResponseDto,
        headers,
        HttpStatus.valueOf(200)
    )
}