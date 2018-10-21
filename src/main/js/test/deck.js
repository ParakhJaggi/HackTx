let decks = require('../decks');
let cards = require('../cards');
let Suit = cards.Suit;
let Rank = cards.Rank;

exports['create deck with 52 cards'] = function (test) {
	let deck = decks.deck();

	test.ok(deck);
	test.equal(typeof deck, 'object');
	test.equal(deck.size(), 52);
};

exports['check card existence'] = function (test) {
	let deck = decks.deck();

	test.ok(deck);

	for (let n1 in Suit) {
		let s = Suit[n1];

		for (let n2 in Rank) {
			let r = Rank[n2];

			test.ok(deck.contains(cards.card(r, s)));
		}
	}
};
