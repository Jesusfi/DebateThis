package com.example.jesusizquierdo.debatethis.Classes

import java.io.Serializable
import java.util.*

/**
 * Created by Jesus Izquierdo on 5/21/2017.
 */
class DiscussionCard(val title: String = "",
                     val category: String = "",
                     val source:String = "",
                     val userDescription: String = "",
                     val url: String = "",
                     val articleTitle: String = "",
                     val userName:String = "",
                     val uniqueKey: String = "") : Serializable