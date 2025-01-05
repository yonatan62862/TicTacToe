package com.example.tictactoegame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Button>
    private lateinit var tvStatus: TextView
    private lateinit var btnReset: Button

    private var currentPlayer = "X"
    private var board = Array(3) { Array(3) { "" } }
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)
        btnReset = findViewById(R.id.btnReset)

        buttons = arrayOf(
            findViewById(R.id.btn0), findViewById(R.id.btn1), findViewById(R.id.btn2),
            findViewById(R.id.btn3), findViewById(R.id.btn4), findViewById(R.id.btn5),
            findViewById(R.id.btn6), findViewById(R.id.btn7), findViewById(R.id.btn8)
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                onCellClicked(button, index / 3, index % 3)
            }
        }

        btnReset.setOnClickListener {
            resetGame()
        }
    }

    private fun onCellClicked(button: Button, row: Int, col: Int) {
        if (!gameActive || board[row][col].isNotEmpty()) return

        board[row][col] = currentPlayer
        button.text = currentPlayer

        if (checkWinner()) {
            tvStatus.text = "Winner: $currentPlayer"
            gameActive = false
            btnReset.visibility = View.VISIBLE
        } else if (isDraw()) {
            tvStatus.text = "It's a Draw!"
            gameActive = false
            btnReset.visibility = View.VISIBLE
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            tvStatus.text = "Player $currentPlayer Turn"
        }
    }

    private fun checkWinner(): Boolean {
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true

        return false
    }

    private fun isDraw(): Boolean {
        return board.all { row -> row.all { it.isNotEmpty() } }
    }

    private fun resetGame() {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameActive = true

        buttons.forEach { it.text = "" }
        tvStatus.text = "Player X Turn"
        btnReset.visibility = View.GONE
    }
}
