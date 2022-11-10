package com.twotwo.planter.util

import org.springframework.stereotype.Component

@Component
class CommonUtil {
    val FILE_SEPARATOR = "_"
    val FILE_EXTENSION_SEPARATOR = "."

    fun buildFileName(category: String, originalFileName: String): String? {
        println(originalFileName)
        val fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR)
        var fileExtension = ".png"
        var fileName = "file"
        if(fileExtensionIndex != -1){
            fileExtension = originalFileName.substring(fileExtensionIndex)
            fileName = originalFileName.substring(0, fileExtensionIndex)
        }
        val now = System.currentTimeMillis().toString()
        return "images/" + category + '/' + fileName + FILE_SEPARATOR + now + fileExtension
    }
}