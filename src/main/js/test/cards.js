let cards = require('../cards');
let Rank = cards.Rank;
let Suit = cards.Suit;

exports['create card with rank and suit'] = function (test) {
	let card = cards.card(Rank.Ace, Suit.Spade);

	test.ok(card);
	test.equal(typeof card, 'object');
	test.equal(card.rank(), Rank.Ace);
	test.equal(card.suit(), Suit.Spade);
};

exports['card with same rank ans suit give the same object'] = function (test) {
	let card1 = cards.card(Rank.Ace, Suit.Spade);
	let card2 = cards.card(Rank.Ace, Suit.Spade);

	test.strictEqual(card1, card2);
};

