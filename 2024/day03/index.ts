import {Day} from "../utils/Day.ts";

export default class Day02 extends Day {
    partOne(): number {
        const regex = /mul\((\d{1,3}),(\d{1,3})\)/g;
        const matches = this.input.matchAll(regex)
        let sum = 0
        for (const match of matches) {
            sum += parseInt(match[1]) * parseInt(match[2])
        }
        return sum
    }

    partTwo(): number {
        const regex = /(do\(\))|(don't\(\))|(mul\((\d{1,3}),(\d{1,3})\))/g;
        const matches = this.input.matchAll(regex)
        let sum = 0
        let enabled = true
        for (const match of matches) {
            if (match[0] === "don't()") enabled = false
            else if (match[0] === "do()") enabled = true
            else if (enabled) sum += parseInt(match[4]) * parseInt(match[5])
        }
        return sum
    }
}