import {Day} from "../utils/Day.ts";

class Node {
    constructor(protected value: number, public children: number[] = []) {
    }
}

export default class Day05 extends Day {
    updates: number[][]
    graph: Map<number, Node> = new Map()
    rules: [number, number][] = []

    constructor(input: string) {
        super(input);
        const [firstSection, secondSection] = this.input.split(/\r?\n\r?\n/)
        for (const rule of firstSection.split(/\r?\n/)) {
            const [left, right] = rule.split('|').map(i => parseInt(i))
            this.rules.push([left, right])
            if (!this.graph.has(left)) this.graph.set(left, new Node(left, [right]))
            else this.graph.get(left)!.children.push(right)
        }
        this.updates = secondSection.split(/\r?\n/).map(i => i.split(',').map(j => parseInt(j)))
    }

    isValid(pages: number[]): boolean {
        let page = pages[0]
        for (let i = 1; i < pages.length; i++) {
            const current = pages[i]
            const node = this.graph.get(page)
            if (!node) return false
            if (!node.children.includes(current)) return false
            page = current
        }
        return true
    }

    topologicalSort(pages: number[]): number[] {
        const graph: Map<number, Node> = new Map()
        for (const [left, right] of this.rules) {
            if (pages.includes(left) && pages.includes(right)) {
                if (!graph.has(left)) graph.set(left, new Node(left, [right]))
                else graph.get(left)!.children.push(right)
            }
        }
        const sortedPages: number[] = []
        const visited = new Set<number>()
        const dfs = (page: number) => {
            if (visited.has(page)) return
            visited.add(page)
            for (const child of graph.get(page)?.children || []) {
                dfs(child)
            }
            if (pages.includes(page)) sortedPages.push(page)
        }

        for (const page of pages) {
            dfs(page)
        }
        return sortedPages.reverse()
    }

    partOne(): number {
        let sum = 0
        for (const pages of this.updates) {
            if (this.isValid(pages)) {
                sum += pages[Math.floor(pages.length / 2)]
            }
        }
        return sum
    }

    partTwo(): number {
        let sum = 0
        for (const pages of this.updates.filter(i => !this.isValid(i))) {
            const sortedPages = this.topologicalSort(pages)
            sum += sortedPages[Math.floor(sortedPages.length / 2)]
        }
        return sum
    }
}