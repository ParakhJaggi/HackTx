const rewire = require("rewire")
const components = rewire("./components")
const buildReduxValidator = components.__get__("buildReduxValidator")
// @ponicode
describe("buildReduxValidator", () => {
    test("0", () => {
        let param2 = [["Edmond", "Pierre Edouard", "Edmond"], ["Anas", "Edmond", "Michael"], ["Anas", "Jean-Philippe", "Pierre Edouard"]]
        let callFunction = () => {
            buildReduxValidator({ spec: () => -29.45, error: () => "Message box: foo; bar\n" }, param2)
        }
    
        expect(callFunction).not.toThrow()
    })

    test("1", () => {
        let param2 = [["Pierre Edouard", "Anas", "Jean-Philippe"], ["George", "George", "Pierre Edouard"], ["Michael", "George", "Edmond"]]
        let callFunction = () => {
            buildReduxValidator({ spec: () => -0.5, error: () => "invalid choice" }, param2)
        }
    
        expect(callFunction).not.toThrow()
    })

    test("2", () => {
        let param2 = [["Michael", "George", "Jean-Philippe"], ["Michael", "Edmond", "George"], ["Jean-Philippe", "Edmond", "Michael"]]
        let callFunction = () => {
            buildReduxValidator({ spec: () => 10.0, error: () => "ValueError" }, param2)
        }
    
        expect(callFunction).not.toThrow()
    })

    test("3", () => {
        let param2 = [["Jean-Philippe", "Michael", "George"], ["Pierre Edouard", "Michael", "Michael"], ["Pierre Edouard", "Jean-Philippe", "Edmond"]]
        let callFunction = () => {
            buildReduxValidator({ spec: () => -29.45, error: () => "ValueError" }, param2)
        }
    
        expect(callFunction).not.toThrow()
    })

    test("4", () => {
        let param2 = [["George", "Pierre Edouard", "Jean-Philippe"], ["Anas", "Pierre Edouard", "Edmond"], ["Anas", "Anas", "Pierre Edouard"]]
        let callFunction = () => {
            buildReduxValidator({ spec: () => 0.5, error: () => "error" }, param2)
        }
    
        expect(callFunction).not.toThrow()
    })

    test("5", () => {
        let callFunction = () => {
            buildReduxValidator(undefined, undefined)
        }
    
        expect(callFunction).not.toThrow()
    })
})
