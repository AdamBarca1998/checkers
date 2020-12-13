package com.example.checkers

import android.app.Activity
import android.widget.ImageButton
import kotlinx.android.synthetic.main.board.*

class InitSachovnica(private val context: Activity) {

    private val sachovnica = arrayOf<ImageButton>()

    init {
        sachovnica[0] = context.imageButton00
        sachovnica[1] = context.imageButton01
        sachovnica[2] = context.imageButton02
        sachovnica[3] = context.imageButton03
        sachovnica[4] = context.imageButton04
        sachovnica[5] = context.imageButton05
        sachovnica[6] = context.imageButton06
        sachovnica[7] = context.imageButton07

        sachovnica[8] = context.imageButton10
        sachovnica[9] = context.imageButton11
        sachovnica[10] = context.imageButton12
        sachovnica[11] = context.imageButton13
        sachovnica[12] = context.imageButton14
        sachovnica[13] = context.imageButton15
        sachovnica[14] = context.imageButton16
        sachovnica[15] = context.imageButton17

        sachovnica[16] = context.imageButton20
        sachovnica[17] = context.imageButton21
        sachovnica[18] = context.imageButton22
        sachovnica[19] = context.imageButton23
        sachovnica[20] = context.imageButton24
        sachovnica[21] = context.imageButton25
        sachovnica[22] = context.imageButton26
        sachovnica[23] = context.imageButton27

        sachovnica[24] = context.imageButton30
        sachovnica[25] = context.imageButton31
        sachovnica[26] = context.imageButton32
        sachovnica[27] = context.imageButton33
        sachovnica[28] = context.imageButton34
        sachovnica[29] = context.imageButton35
        sachovnica[30] = context.imageButton36
        sachovnica[31] = context.imageButton37

        sachovnica[32] = context.imageButton40
        sachovnica[33] = context.imageButton41
        sachovnica[34] = context.imageButton42
        sachovnica[35] = context.imageButton43
        sachovnica[36] = context.imageButton44
        sachovnica[37] = context.imageButton45
        sachovnica[38] = context.imageButton46
        sachovnica[39] = context.imageButton47

        sachovnica[40] = context.imageButton50
        sachovnica[41] = context.imageButton51
        sachovnica[42] = context.imageButton52
        sachovnica[43] = context.imageButton53
        sachovnica[44] = context.imageButton54
        sachovnica[45] = context.imageButton55
        sachovnica[46] = context.imageButton56
        sachovnica[47] = context.imageButton57

        sachovnica[48] = context.imageButton60
        sachovnica[49] = context.imageButton61
        sachovnica[50] = context.imageButton62
        sachovnica[51] = context.imageButton63
        sachovnica[52] = context.imageButton64
        sachovnica[53] = context.imageButton65
        sachovnica[54] = context.imageButton66
        sachovnica[55] = context.imageButton67

        sachovnica[56] = context.imageButton70
        sachovnica[57] = context.imageButton71
        sachovnica[58] = context.imageButton72
        sachovnica[59] = context.imageButton73
        sachovnica[60] = context.imageButton74
        sachovnica[61] = context.imageButton75
        sachovnica[62] = context.imageButton76
        sachovnica[63] = context.imageButton77
    }

    fun getSachovnica(): Array<ImageButton> {
        return sachovnica
    }
}