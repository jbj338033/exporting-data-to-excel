package com.exportingdatatoexcel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class ExportingDataToExcelApplication

fun main(args: Array<String>) {
    runApplication<ExportingDataToExcelApplication>(*args)
}
