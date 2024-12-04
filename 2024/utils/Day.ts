function measureTimedValue(handler: () => unknown) {
    const start = performance.now()
    const value = handler()
    const end = performance.now()

    return {value, duration: end - start}
}

export abstract class Day {
    protected input: string
    get inputAsLines(): string[] {
        return this.input.split(/\r?\n/)
    }

    constructor(input: string) {
        this.input = input
    }

    solve() {
        const partOne = measureTimedValue(this.partOne.bind(this))
        const partTwo = measureTimedValue(this.partTwo.bind(this))

        console.log(`Part 1: ${partOne.value} in ${partOne.duration.toFixed(3)}ms`);
        console.log(`Part 2: ${partTwo.value} in ${partTwo.duration.toFixed(3)}ms`);
    }

    abstract partOne(): unknown;
    abstract partTwo(): unknown;
}