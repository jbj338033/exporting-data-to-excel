package com.exportingdatatoexcel.utils

import com.exportingdatatoexcel.domain.entity.Post
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.ByteArrayOutputStream
import java.time.format.DateTimeFormatter

object ExcelExporter {
    private val HEADERS = listOf("ID", "제목", "내용", "생성일자")
    private val DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun createPostExcel(posts: List<Post>): ByteArray {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Posts")

        println(posts)

        createHeaderRow(workbook, sheet)
        createDataRows(sheet, posts)
        adjustColumns(sheet)

        return convertToByteArray(workbook)
    }

    private fun createHeaderRow(workbook: XSSFWorkbook, sheet: Sheet) {
        val headerStyle = createHeaderStyle(workbook)
        val headerRow = sheet.createRow(0)

        HEADERS.forEachIndexed { index, header ->
            headerRow.createCell(index).apply {
                setCellValue(header)
                cellStyle = headerStyle
            }
        }
    }

    private fun createHeaderStyle(workbook: XSSFWorkbook): CellStyle {
        return workbook.createCellStyle().apply {
            fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
            fillPattern = FillPatternType.SOLID_FOREGROUND
            setFont(workbook.createFont().apply { bold = true })
        }
    }

    private fun createDataRows(sheet: Sheet, posts: List<Post>) {
        println(posts)
        posts.forEachIndexed { index, post ->
            println(post)
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(post.id?.toString() ?: "")
            row.createCell(1).setCellValue(post.title)
            row.createCell(2).setCellValue(post.content)
            row.createCell(3).setCellValue(post.createdAt!!.format(DATE_FORMATTER))
        }
    }

    private fun adjustColumns(sheet: Sheet) {
        HEADERS.indices.forEach { sheet.autoSizeColumn(it) }
    }

    private fun convertToByteArray(workbook: XSSFWorkbook): ByteArray {
        return ByteArrayOutputStream().use { outputStream ->
            workbook.use { it.write(outputStream) }
            outputStream.toByteArray()
        }
    }
}