import {Day} from "../utils/Day.ts";

const DIRECTIONS = [
    [0, 1],
    [1, 1],
    [1, 0],
    [1, -1],
    [0, -1],
    [-1, -1],
    [-1, 0],
    [-1, 1]
] as const

export default class Day04 extends Day {
    maxRow: number
    maxCol: number

    constructor(input: string) {
        super(input);
        this.maxRow = this.inputAsLines.length - 1
        this.maxCol = this.inputAsLines[0].length - 1
    }

    isXmasWord(startRow: number, startCol: number, direction: readonly [number, number]): boolean {
        let row = startRow
        let col = startCol
        for (let char of "MAS") {
            row = row + direction[0]
            col = col + direction[1]
            if (Math.min(row, col) < 0 || row > this.maxRow || col > this.maxCol) return false
            if (this.inputAsLines[row][col] !== char) return false
        }
        return true
    }

    isXMAS(row: number, col: number): boolean {
        if (Math.min(row - 1, col - 1) < 0 || row + 1 > this.maxRow || col + 1 > this.maxCol) return false
        const upperLeft = this.inputAsLines[row - 1][col - 1]
        const upperRight = this.inputAsLines[row - 1][col + 1]
        const lowerLeft = this.inputAsLines[row + 1][col - 1]
        const lowerRight = this.inputAsLines[row + 1][col + 1]
        return ["SM", "MS"].includes(upperLeft + lowerRight)
            && ["SM", "MS"].includes(upperRight + lowerLeft);

    }

    partOne(): number {
        let occurrences = 0
        for (let row = 0; row < this.inputAsLines.length; row++) {
            for (let col = 0; col < this.inputAsLines[row].length; col++) {
                if (this.inputAsLines[row][col] === 'X') {
                    for (const direction of DIRECTIONS) {
                        if (this.isXmasWord(row, col, direction)) {
                            occurrences++
                        }
                    }
                }
            }
        }
        return occurrences
    }

    partTwo(): number {
        let occurrences = 0
        for (let row = 0; row < this.inputAsLines.length; row++) {
            for (let col = 0; col < this.inputAsLines[row].length; col++) {
                if (this.inputAsLines[row][col] === 'A') {
                    if (this.isXMAS(row, col)) {
                        occurrences++
                    }
                }
            }
        }
        return occurrences
    }
}