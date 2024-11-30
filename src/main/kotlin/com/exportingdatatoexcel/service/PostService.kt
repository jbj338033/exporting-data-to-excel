package com.exportingdatatoexcel.service

import com.exportingdatatoexcel.domain.entity.Post
import com.exportingdatatoexcel.dto.request.PostCreateRequest
import com.exportingdatatoexcel.dto.response.PostResponse
import com.exportingdatatoexcel.repository.PostRepository
import com.exportingdatatoexcel.utils.ExcelExporter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository
) {
    @Transactional(readOnly = true)
    fun getPosts(): List<PostResponse> {
        val posts = postRepository.findAll()

        return posts.map { PostResponse.of(it) }
    }

    @Transactional(readOnly = true)
    fun getPost(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalArgumentException("Post not found")

        return PostResponse.of(post)
    }

    @Transactional
    fun createPost(request: PostCreateRequest) {
        val post = Post(
            title = request.title,
            content = request.content
        )

        postRepository.save(post)
    }

    @Transactional
    fun deletePost(postId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalArgumentException("Post not found")

        postRepository.delete(post)
    }

    @Transactional(readOnly = true)
    fun exportPostsToExcel(): ByteArray {
        val posts = postRepository.findAll()

        return ExcelExporter.createPostExcel(posts)
    }
}