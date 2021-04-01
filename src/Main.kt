val esc: String = Character.toString(27)
val startBlue = "$esc[30;44m"
val startGrey = "$esc[30;47m"
val startWhite = "$esc[30;30m"
val end = "$esc[0m"
val blueSquare = "$startBlue   $end"
val invalid = "Invalid response."
val blackPawn = Pair("P", "b")
val blackQueen = Pair("Q", "b")
val blackKing = Pair("K", "b")
val blackHorse = Pair("H", "b")
val blackTower = Pair("T", "b")
val blackBishop = Pair("B", "b")
val whitePawn = Pair("P", "w")
val whiteQueen = Pair("Q", "w")
val whiteKing = Pair("K", "w")
val whiteHorse = Pair("H", "w")
val whiteTower = Pair("T", "w")
val whiteBishop = Pair("B", "w")

fun firstLine(numColumns: Int): String {
    var firstLine = ""
    var countColumns = 0
    var letter = 'A'
    while (countColumns < numColumns + 2){
        if (countColumns == 0 || countColumns == numColumns + 1) {
            firstLine += blueSquare
        } else {
            firstLine += "$startBlue $letter $end"
            if (letter == 'Z') {
                letter = 'A'
            } else {
                letter++
            }
        }
        countColumns++
    }
    return "$firstLine\n"
}
fun lines (countNum: Int, countLines: Int, numColumns: Int, showLegend: Boolean = true, showPieces: Boolean = true,
           pieces: Array<Pair<String,String>?>): String {
    var position = if (countLines == 0) 0 else (countLines * numColumns)
    var countColumns = if (showLegend) 0 else 1
    var line = ""
    while (countColumns < if (showLegend) numColumns + 2 else numColumns + 1) {
        if (showLegend && (countColumns == 0 || countColumns == numColumns + 1)) {
            line += if (countColumns == numColumns + 1) {
                blueSquare
            } else {
                if (countNum < 10) {
                    "$startBlue $countNum $end"
                } else {
                    "$startBlue$countNum $end"
                }
            }
        } else if (countLines % 2 == 0) {
            if (countColumns % 2 != 0) {
                line += if (pieces[position] != null && showPieces) {
                    "$startWhite ${convertStringToUnicode(pieces[position]!!.first, pieces[position]!!.second)} $end"
                } else {
                    "$startWhite   $end"
                }
                position++
            } else if (countColumns % 2 == 0) {
                line += if (pieces[position] != null && showPieces) {
                    "$startGrey ${convertStringToUnicode(pieces[position]!!.first, pieces[position]!!.second)} $end"
                } else {
                    "$startGrey   $end"
                }
                position++
            }
        } else {
            if (countColumns % 2 == 0) {
                line += if (pieces[position] != null && showPieces) {
                    "$startWhite ${convertStringToUnicode(pieces[position]!!.first, pieces[position]!!.second)} $end"
                } else {
                    "$startWhite   $end"
                }
                position++
            } else if (countColumns % 2 != 0) {
                line += if (pieces[position] != null && showPieces) {
                    "$startGrey ${convertStringToUnicode(pieces[position]!!.first, pieces[position]!!.second)} $end"
                } else {
                    "$startGrey   $end"
                }
                position++
            }
        }
        countColumns++
    }
    return "$line\n"
}
fun lastLine (numColumns: Int): String {
    var countColumns = 0
    var lastLine = ""
    while (countColumns < numColumns + 2) {
        lastLine += blueSquare
        countColumns++
    }
    return "$lastLine\n"
}
fun buildMenu(): String = "Welcome to the Chess Board Game!\n" + "1-> Start New Game;\n" + "2-> Exit Game.\n"
fun buildBoard(numColumns: Int, numLines: Int, showLegend: Boolean = false, showPieces: Boolean = false,
               pieces: Array<Pair<String,String>?>) : String {
    var board = ""
    var countLines = 0
    var countNum = 1
    var position = 0
    if (showLegend) {
        board += firstLine(numColumns)
    }
    while(countLines < numLines) {
        board += lines(countNum, countLines, numColumns, showLegend, showPieces, pieces)
        countLines++
        countNum++
        position += numColumns
    }
    if (showLegend) {
        board += lastLine(numColumns)
        return board
    }
    return board
}
fun checkIsNumber(number: String): Boolean {
    number.toIntOrNull() ?: return false
    return true
}
fun checkName(name: String): Boolean {
    if (name == "") {
        return false
    }
    var count = 0
    var countSpace = 0
    while (count < name.length - 1) {
        if (name[0] !in 'A'..'Z') {
            return false
        }
        if (name[count] == ' ') {
            countSpace++
            if (name[count + 1] !in 'A'..'Z') {
                return false
            }
        }
        count++
    }
    if (countSpace < 1) {
        return false
    }
    return true
}
fun showChessLegendOrPieces(message: String): Boolean? {
    if (message == "y" || message == "Y") {
        return true
    }
    if (message == "n" || message == "N") {
        return false
    }
    return null
}
fun board8x8() : Array<Pair<String,String>?> {
    val pieces = arrayOfNulls<Pair<String,String>?>(64)
    var position = 0
    while (position <= 63) {
        while (position in 0..15 || position in 48..63) {
            when (position) {
                0, 7 -> pieces[position] = blackTower
                8, 9, 10, 11, 12, 13, 14, 15 -> pieces[position] = blackPawn
                1, 6 -> pieces[position] = blackHorse
                2, 5 -> pieces[position] = blackBishop
                3 -> pieces[position] = blackQueen
                4 -> pieces[position] = blackKing
                48, 49, 50, 51, 52, 53, 54, 55 -> pieces[position] = whitePawn
                56, 63 -> pieces[position] = whiteTower
                57, 62 -> pieces[position] = whiteHorse
                58, 61 -> pieces[position] = whiteBishop
                59 -> pieces[position] = whiteKing
                60 -> pieces[position] = whiteQueen
            }
            position++
        }
        position++
    }
    return pieces
}
fun board7x7() : Array<Pair<String,String>?> {
    val pieces = arrayOfNulls<Pair<String,String>?>(49)
    var position = 0
    while (position <= 48) {
        while (position in 0..13 || position in 35..48) {
            when (position) {
                0, 6 -> pieces[position] = blackTower
                7, 8, 9, 10, 11, 12, 13 -> pieces[position] = blackPawn
                1, 5 -> pieces[position] = blackHorse
                2, 4 -> pieces[position] = blackBishop
                3 -> pieces[position] = blackKing
                35, 36, 37, 38, 39, 40, 41 -> pieces[position] = whitePawn
                42, 48 -> pieces[position] = whiteTower
                43, 47 -> pieces[position] = whiteHorse
                44, 46 -> pieces[position] = whiteBishop
                45 -> pieces[position] = whiteKing
            }
            position++
        }
        position++
    }
    return pieces
}
fun board6x7() : Array<Pair<String,String>?> {
    val pieces = arrayOfNulls<Pair<String,String>?>(42)
    var position = 0
    while (position <= 41) {
        while (position in 0..11 || position in 30..41) {
            when (position) {
                0 -> pieces[position] = blackTower
                6, 7, 8, 9, 10, 11 -> pieces[position] = blackPawn
                5 -> pieces[position] = blackHorse
                1, 4 -> pieces[position] = blackBishop
                2 -> pieces[position] = blackQueen
                3 -> pieces[position] = blackKing
                30, 31, 32, 33, 34, 35 -> pieces[position] = whitePawn
                36 -> pieces[position] = whiteTower
                41 -> pieces[position] = whiteHorse
                37, 40 -> pieces[position] = whiteBishop
                38 -> pieces[position] = whiteKing
                39 -> pieces[position] = whiteQueen
            }
            position++
        }
        position++
    }
    return pieces
}
fun board6x6() : Array<Pair<String,String>?> {
    val pieces = arrayOfNulls<Pair<String,String>?>(36)
    var position = 0
    while (position <= 35) {
        while (position in 0..11 || position in 24..35) {
            when (position) {
                5 -> pieces[position] = blackTower
                6, 7, 8, 9, 10, 11 -> pieces[position] = blackPawn
                0 -> pieces[position] = blackHorse
                1, 4 -> pieces[position] = blackBishop
                2 -> pieces[position] = blackQueen
                3 -> pieces[position] = blackKing
                24, 25, 26, 27, 28, 29 -> pieces[position] = whitePawn
                35 -> pieces[position] = whiteTower
                30 -> pieces[position] = whiteHorse
                31, 34 -> pieces[position] = whiteBishop
                32 -> pieces[position] = whiteKing
                33 -> pieces[position] = whiteQueen
            }
            position++
        }
        position++
    }
    return pieces
}
fun board4x4() : Array<Pair<String,String>?> {
    val pieces = arrayOfNulls<Pair<String,String>?>(16)
    var position = 0
    while (position <= 15) {
        while (position in 2..3 || position in 12..13) {
            when (position) {
                2 -> pieces[position] = blackTower
                3 -> pieces[position] = blackBishop
                12 -> pieces[position] = whiteTower
                13 -> pieces[position] = whiteQueen
            }
            position++
        }
        position++
    }
    return pieces
}
fun createInitialBoard(numColumns: Int, numLines: Int): Array<Pair<String,String>?> {
    return if (numColumns == 8 && numLines == 8) {
        board8x8()
    } else if (numColumns == 7 && numLines == 7) {
        board7x7()
    } else if (numColumns == 6 && numLines == 6) {
        board6x6()
    } else if (numColumns == 4 && numLines == 4) {
        board4x4()
    } else if (numColumns == 6 && numLines == 7) {
        board6x7()
    } else {
        emptyArray()
    }
}
fun createTotalPiecesAndTurn(numColumns: Int, numLines: Int): Array<Int?> {
    return if ((numColumns in 5..8 && numLines in 5..8 && numColumns == numLines) || (numColumns == 6 && numLines == 7)){
        arrayOf(numColumns * 2, numColumns * 2, 0)
    } else if (numColumns == 4 && numLines == 4) {
        arrayOf(2, 2, 0)
    } else {
        return emptyArray()
    }
}
fun convertStringToUnicode(piece: String, color: String): String {
    return when (piece) {
        "P" -> if (color == "b") "\u265F" else "\u2659"
        "T" -> if (color == "b") "\u265C" else "\u2656"
        "B" -> if (color == "b") "\u265D" else "\u2657"
        "H" -> if (color == "b") "\u265E" else "\u2658"
        "K" -> if (color == "b") "\u265A" else "\u2654"
        "Q" -> if (color == "b") "\u265B" else "\u2655"
        else -> return " "
    }
}
fun getCoordinates (readText: String?): Pair<Int, Int>? {
    var coordinates = Pair(0, 0)
    if ((readText != null && (readText != "" && readText.length == 2))) {
        if (checkIsNumber(readText[0].toString()) && (readText[1] in 'a'..'z' || readText[1] in 'A'..'Z')) {
            var countLines = 1
            var letter = 'a'
            var capitalLetter = 'A'
            while (countLines <= 8) {
                when (readText[1]) {
                    letter, capitalLetter -> coordinates = Pair(readText[0].toString().toInt(), countLines)
                }
                countLines++
                letter++
                capitalLetter++
            }
        } else {
            return null
        }
        return coordinates
    }
    return null
}
fun checkRightPieceSelected(pieceColor: String, turn: Int): Boolean {
    if (turn == 0 && pieceColor == "w") {
        return true
    }
    if (turn == 1 && pieceColor == "b") {
        return true
    }
    return false
}
fun isCoordinateInsideChess (coord: Pair<Int, Int>, numColumns: Int, numLines: Int): Boolean {
    return coord.first in 1..numLines && coord.second in 1..numColumns
}
fun startNewGame (whitePlayer: String, blackPlayer: String, pieces : Array<Pair<String, String>?>,
                  totalPiecesAndTurn : Array<Int?>, numColumns: Int,numLines: Int, showLegend: Boolean= false,
                  showPieces : Boolean = false) {
    val totalPiecesAndTurn = totalPiecesAndTurn as Array<Int>
    do {
        val player = if (totalPiecesAndTurn[2] == 0) whitePlayer else blackPlayer
        do {
            println(buildBoard(numColumns, numLines, showLegend, showPieces, pieces))
            println("$player, choose a piece (e.g 2D).\nMenu-> m;\n")
            val starterPiece = read()
            if (starterPiece == "m") return
            var rightPiece = false
            var rightTargetPiece = false
            if (getCoordinates(starterPiece) != null) {
                if (isCoordinateInsideChess(getCoordinates(starterPiece)!!, numColumns, numLines)) {
                    if (pieces[convertIndex(getCoordinates(starterPiece), numColumns)] != null) {
                        val pieceColor = pieces[convertIndex(getCoordinates(starterPiece), numColumns)]!!.second
                        rightPiece = checkRightPieceSelected(pieceColor, totalPiecesAndTurn[2])
                        if (!rightPiece) println(invalid())
                    } else {
                        println(invalid())
                    }
                } else {
                    println(invalid())
                }
            } else {
                println(invalid())
            }
            if (rightPiece) {
                println("$player, choose a target piece (e.g 2D).\nMenu-> m;\n")
                val targetPiece = read()
                if (targetPiece == "m") return
                if (isCoordinateInsideChess(getCoordinates(targetPiece)!!, numColumns, numLines)) {
                    rightTargetPiece = movePiece(pieces, numColumns, numLines,
                        getCoordinates(starterPiece)!!, getCoordinates(targetPiece)!!, totalPiecesAndTurn
                    )
                    if (!rightTargetPiece) {
                        println(invalid())
                    }
                } else {
                    println(invalid())
                }
            }
        } while (!rightPiece || !rightTargetPiece)
    } while (totalPiecesAndTurn[0] != 0 && totalPiecesAndTurn[1] != 0)
    if (totalPiecesAndTurn[0] == 0){
        println("Congrats! $blackPlayer wins!")
    }else {
        println("Congrats! $whitePlayer wins!")
    }
}
fun convertIndex(coordinate : Pair<Int, Int>?, numColumns: Int): Int {
    val (x, y) = coordinate!!
    return y - 1 + (x - 1) * numColumns
}
fun isHorseValid(currentCoord: Pair<Int, Int>,targetCoord : Pair<Int, Int>,
                 pieces : Array<Pair<String, String>?>, numColumns: Int, numLines: Int): Boolean {
    val (x, y) = currentCoord
    val (x1, y1) = targetCoord
    var count = 1
    while (count >= -1) {
        if ((y + count * 2 == y1 && (x + 1 == x1 || x - 1 == x1)) || (x + count * 2 == x1 && (y + 1 == y1 || y - 1 == y1))){
            if (pieces[convertIndex(targetCoord, numColumns)] == null ||
                    pieces[convertIndex(targetCoord, numColumns)]?.second != pieces[convertIndex(currentCoord, numColumns)]?.second) {
                return true
            }
        }
        count -= 2
    }
    return false
}
fun isKingValid(currentCoord: Pair<Int, Int>,targetCoord : Pair<Int, Int>,
                pieces: Array<Pair<String, String>?>,numColumns: Int,numLines: Int):Boolean {
    if (isKnightValid(currentCoord,targetCoord,pieces,numColumns,numLines) ||
            isBishopValid(currentCoord,targetCoord,pieces,numColumns,1) ||
            isTowerValid(currentCoord,targetCoord,pieces,numColumns,1)) {
        if (pieces[convertIndex(targetCoord, numColumns)] == null||
                pieces[convertIndex(targetCoord, numColumns)]?.second != pieces[convertIndex(currentCoord, numColumns)]?.second){
            return true
        }
    }
    return false
}
fun isTowerValid(currentCoord: Pair<Int, Int>,targetCoord: Pair<Int, Int>,
                 pieces: Array<Pair<String, String>?>,numColumns: Int,numLines: Int):Boolean {
    val (x, y) = currentCoord
    val (x1, y1) = targetCoord
    var count = 1
    while (count <= numLines) {
        if (((x + count == x1 || x - count == x1) && y == y1) || ((y + count == y1 || y - count == y1) && x == x1)) {
            if (pieces[convertIndex(targetCoord, numColumns)] == null
                    || pieces[convertIndex(targetCoord, numColumns)]?.second != pieces[convertIndex(currentCoord, numColumns)]?.second){
                return true
            }
        }
        count++
    }
    return false
}
fun isBishopValid(currentCoord: Pair<Int, Int>,targetCoord: Pair<Int, Int>,
                  pieces: Array<Pair<String, String>?>,numColumns: Int,numLines: Int):Boolean {
    val (x, y) = currentCoord
    val (x1, y1) = targetCoord
    var count = 1
    while (count <= numLines) {
        if (((x + count == x1|| x - count == x1) && y + count == y1)
                || ((x + count == x1 || x - count == x1) && y - count == y1)) {
            if (pieces[convertIndex(targetCoord, numColumns)] == null ||
                    pieces[convertIndex(targetCoord, numColumns)]?.second != pieces[convertIndex(currentCoord, numColumns)]?.second) {
                return true
            }
        }
        count++
    }
    return false
}
fun isQueenValid(currentCoord: Pair<Int, Int>,targetCoord: Pair<Int, Int>,
                 pieces: Array<Pair<String, String>?>,numColumns: Int,numLines: Int):Boolean {
    if (isBishopValid(currentCoord,targetCoord,pieces,numColumns,numLines) ||
        isTowerValid(currentCoord,targetCoord,pieces,numColumns,numLines)) {
        if (pieces[convertIndex(targetCoord, numColumns)] == null ||
                pieces[convertIndex(targetCoord, numColumns)]?.second != pieces[convertIndex(currentCoord, numColumns)]?.second) {
            return true
        }
    }

    return false
}
fun isKnightValid(currentCoord: Pair<Int, Int>,targetCoord: Pair<Int, Int>,
                  pieces: Array<Pair<String, String>?>,numColumns: Int,numLines: Int):Boolean {
    val (x1,y1) = targetCoord
    val (x,y) = currentCoord
    if (((x + 1 == x1 || x - 1 == x1) && y == y1)) {
        if (pieces[convertIndex(targetCoord, numColumns)] == null ||
                pieces[convertIndex(targetCoord, numColumns)]?.second != pieces[convertIndex(currentCoord, numColumns)]?.second){
            return true
        }
    }
    return false
}
fun isValidTargetPiece(currentSelectedPiece : Pair<String, String>,currentCoord: Pair<Int, Int>,
                       targetCoord : Pair<Int, Int>, pieces : Array<Pair<String, String>?>,
                       numColumns: Int, numLines: Int): Boolean {
    if (currentSelectedPiece.first == "P"){
        return isKnightValid(currentCoord,targetCoord,pieces,numColumns,numLines)
    }
    if (currentSelectedPiece.first == "Q"){
        return isQueenValid(currentCoord,targetCoord,pieces,numColumns,numLines)
    }
    if (currentSelectedPiece.first == "T"){
        return isTowerValid(currentCoord,targetCoord,pieces,numColumns,numLines)
    }
    if (currentSelectedPiece.first == "B"){
        return isBishopValid(currentCoord,targetCoord,pieces,numColumns,numLines)
    }
    if (currentSelectedPiece.first == "H"){
        return isHorseValid(currentCoord,targetCoord,pieces,numColumns,numLines)
    }
    if (currentSelectedPiece.first == "K"){
        return isKingValid(currentCoord,targetCoord,pieces,numColumns,numLines)
    }
    return false
}
fun movePiece(pieces : Array<Pair<String, String>?>, numColumns: Int, numLines: Int, currentCoord: Pair<Int, Int>,
              targetCoord: Pair<Int, Int>, totalPiecesAndTurn : Array<Int>): Boolean {
    val currentSelectedPiece = pieces[convertIndex(currentCoord, numColumns)]!!
    if (isValidTargetPiece(currentSelectedPiece, currentCoord, targetCoord, pieces, numColumns, numLines)) {
        if (pieces[convertIndex(targetCoord, numColumns)] != null) {
            if (totalPiecesAndTurn[2] == 0) {
                totalPiecesAndTurn[1]--
            } else {
                totalPiecesAndTurn[0]--
            }
        }
        pieces[convertIndex(targetCoord, numColumns)] = pieces[convertIndex(currentCoord, numColumns)]
        pieces[convertIndex(currentCoord, numColumns)] = null
        if (totalPiecesAndTurn[2] == 0) totalPiecesAndTurn[2] = 1 else totalPiecesAndTurn[2] = 0
        return true
    }
    return false
}
fun read() = readLine() ?: invalid
fun invalid() = invalid
fun getValidName(question: String): String {
    var player: String
    do {
        println(question + "\n")
        player = read()
        if (!checkName(player)) {
            println(invalid())
        }
    } while (!checkName(player))
    return player
}
fun getValidLegendPieces(question: String): String {
    var legendPieces: String
    do {
        println("$question\n")
        legendPieces = read()
        if (showChessLegendOrPieces(legendPieces) == null) {
            println(invalid())
        }
    } while (showChessLegendOrPieces(legendPieces) == null)
    return legendPieces
}
fun getValidColumnsLines(): Pair<String,String> {
    var numColumns: String
    var numLines : String
    do{
        do {
            println("How many chess columns?\n")
            numColumns = read()
            if (!checkIsNumber(numColumns)) {
                println(invalid())
            }
        } while(!checkIsNumber(numColumns))
        println("How many chess lines?\n")
        numLines = read()
        if (!((checkIsNumber(numLines) && numLines.toInt() != 5 && numColumns == numLines && numLines.toInt() in 4..8)
                        || (numColumns == "6" && numLines == "7"))) {
            println(invalid())
        }
    } while (!((checkIsNumber(numLines) && numLines.toInt() != 5 && numColumns == numLines && numLines.toInt() in 4..8)
                    || (numColumns == "6" && numLines == "7")))

    return Pair(numColumns, numLines)
}
fun main() {
    println(buildMenu())
    var choice = readLine() ?: invalid
    while (choice == "1") {
        val player1 = getValidName("First player name?")
        val player2 = getValidName("Second player name?")
        val (numColumns, numLines) = getValidColumnsLines()
        val showLegend = getValidLegendPieces("Show legend (y/n)?")
        val showPieces = getValidLegendPieces("Show pieces (y/n)?")
        startNewGame(player1,player2,createInitialBoard(numColumns.toInt(), numLines.toInt()),
                createTotalPiecesAndTurn(numColumns.toInt(),numLines.toInt()),numColumns.toInt(),
                numLines.toInt(),showChessLegendOrPieces(showLegend)!!,showChessLegendOrPieces(showPieces)!!)
        println("1-> Start New Game;\n2-> Exit Game.\n")
        choice = readLine() ?: invalid
    }
}