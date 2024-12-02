import {Day} from "../utils/Day.ts";

class Report {
    constructor(protected values: number[] = []) {}

    isSafe(): boolean {
        return this.check(this.values)
    }

    isSafeWithTolerance(): boolean {
        if (this.isSafe()) return true
        for (let i = 0; i < this.values.length; i++) {
            const copy = [...this.values]
            copy.splice(i, 1)
            if (this.check(copy)) return true
        }
        return false
    }

    private check(values: number[]): boolean {
        const isIncreasing = values[0] < values[1]
        for (let i = 0; i < values.length - 1; i++) {
            const delta = Math.abs(values[i] - values[i + 1])
            const isCurrentIncreasing = values[i] < values[i + 1]

            if (delta > 3 || delta === 0) return false
            if (isCurrentIncreasing !== isIncreasing) return false
        }
        return true
    }
}

export default class Day02 extends Day {
    reports: Report[] = []

    constructor(input: string) {
        super(input);

        for (const line of this.inputAsLines) {
            this.reports.push(new Report(line.split(' ').map(i => parseInt(i))))
        }
    }

    partOne(): number {
        return this.reports.filter(report => report.isSafe()).length
    }

    partTwo(): number {
        return this.reports.filter(report => report.isSafeWithTolerance()).length
    }
}