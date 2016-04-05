/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domaine;

/**
 *
 * @author Fred
 */
public class Case {

    private int x;
    private int y;

    public Case() {
    }

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Case cases) {
        return (cases.x == this.x && cases.y == this.y);
    }

    public boolean equals(Coup coup) {
        return (coup.x == this.x && coup.y == this.y);
    }
}
