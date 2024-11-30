package com.exportingdatatoexcel.controller

import com.exportingdatatoexcel.dto.request.PostCreateRequest
import com.exportingdatatoexcel.dto.response.PostResponse
import com.exportingdatatoexcel.service.PostService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService
) {
    companion object {
        private val EXCEL_MEDIA_TYPE = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        private val FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
    }

    @GetMapping
    fun getPosts() = postService.getPosts()

    @GetMapping("/{postId}")
    fun getPost(@PathVariable postId: Long) = postService.getPost(postId)

    @PostMapping
    fun createPost(@RequestBody request: PostCreateRequest) = postService.createPost(request)

    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: Long) = postService.deletePost(postId)

    @GetMapping("/excel", produces = ["application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"])
    fun exportPostsToExcel(): ResponseEntity<ByteArray> {
        val excelData = postService.exportPostsToExcel()
        val fileName = "posts_${LocalDateTime.now().format(FILE_NAME_FORMATTER)}.xlsx"

        return ResponseEntity.ok()
            .contentType(EXCEL_MEDIA_TYPE)
            .header(HttpHeaders.CONTENT_DISPOSITION, """attachment; filename="$fileName"""")
            .body(excelData)
    }
}