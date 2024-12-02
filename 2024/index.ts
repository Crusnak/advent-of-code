import {argv} from 'bun'

const dayNumber = argv[2] ? parseInt(argv[2]) : new Date().getUTCDate()
const dayNumberPadded = `${dayNumber}`.padStart(2, '0')

const inputFile = Bun.file(`./day${dayNumberPadded}/input.txt`)
if (!inputFile.exists()) {
    throw new Error(`Input file for day ${dayNumberPadded} does not exist.`)
}

const Day = (await import(`./day${dayNumberPadded}/index.ts`)).default
const day = new Day(await inputFile.text())
day.solve()