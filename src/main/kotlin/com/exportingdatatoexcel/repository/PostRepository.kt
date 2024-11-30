package com.exportingdatatoexcel.repository

import com.exportingdatatoexcel.domain.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long> {
}