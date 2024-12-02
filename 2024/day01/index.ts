import {Day} from "../utils/Day.ts";

export default class Day01 extends Day {
    partOne(): number {
        const leftList: number[] = [], rightList: number[] = []
        for (const line of this.inputAsLines) {
            const [left, right] = line.split("   ")
            leftList.push(parseInt(left))
            rightList.push(parseInt(right))
        }

        leftList.sort()
        rightList.sort()

        let sum = 0
        for (let i = 0; i < leftList.length; i++) {
            sum += Math.abs(leftList[i] - rightList[i])
        }
        return sum
    }

    partTwo(): number {
        const leftList: number[] = []
        const occurrences: Map<number, number> = new Map()
        for (const line of this.inputAsLines) {
            const [left, right] = line.split("   ")
            leftList.push(parseInt(left))
            occurrences.set(parseInt(right), (occurrences.get(parseInt(right)) ?? 0) + 1)
        }
        let sum = 0
        leftList.forEach(item => {
            const count = occurrences.get(item) ?? 0
            sum += item * count
        })
        return sum
    }
}