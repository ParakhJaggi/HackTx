const webpack = require('webpack');
const UglifyJsPlugin = webpack.optimize.UglifyJsPlugin;
const ExtractTextPlugin = require("extract-text-webpack-plugin");
const path = require('path');
const env  = require('yargs').argv.env;
const glob = require('glob');

const root = path.resolve(__dirname);
const src = path.join(root, 'src/main');
const test = path.join(root, 'src/test');
const dest = path.join(root, 'build');

let main = 'js/app.js';
let libraryName = 'petfinder-site';
let outputFile = '';
let port = 3000;

const config = {
	context: src,
	output: {
		path: path.resolve(dest, 'statics'),
		publicPath: 'http://localhost:' + port + '/'
	},
	module: {
		rules: [{
				test: /\.js$/,
				use: [ 'babel-loader' ],
				exclude: /node_modules/
			}, {
				test: /.(png|jpg|jpeg|gif|svg|woff|woff2|ttf|ico|eot)$/,
				use: "url-loader?limit=100000"
			}]
	},
	resolve: {
		alias: {
			js: path.resolve(src, 'js'),
			styles: path.resolve(src, 'styles')
		},
		extensions: ['.json', '.js', '.scss']
	},
	plugins: [
		new webpack.HotModuleReplacementPlugin(),
		new webpack.NamedModulesPlugin()
	],
};

if(env === 'dev') {
	config.devServer = {
		hot: true,
		port: port,
		https: false,
		contentBase: dest,
		publicPath: 'http://localhost:' + port + '/',
		headers: {
			'access-control-allow-origin': '*'
		}
	};

	config.entry = [
		'babel-polyfill',
		'react-hot-loader/patch',
		'webpack-dev-server/client?http://localhost:' + port + '/',
		'webpack/hot/only-dev-server',
		path.resolve(src, main)
	];

	config.devtool = 'cheap-module-eval-source-map';

	config.module.rules.push({
		test: /\.scss$/,
		use: [{
			loader: "style-loader" // creates style nodes from JS strings
		}, {
			loader: "css-loader" // translates CSS into CommonJS
		}, {
			loader: "sass-loader" // compiles Sass to CSS
		}]
	});

	outputFile = libraryName + '.js';
}
else if(env === 'test') {
	config.context = test;
	config.entry = glob.sync(path.resolve(test,'js/**/*test.js'));

	config.externals = {
		'react/lib/ExecutionEnvironment': true,
		'react/addons': true,
		'react/lib/ReactContext': true
	};

	config.output.path = path.resolve(dest, 'tests');

	outputFile = libraryName + '.test.js';
}
else {
	if(env === 'build') {
		config.devtool = 'cheap-source-map';
		config.output.sourceMapFilename = libraryName + '.map';
		config.plugins.push(new UglifyJsPlugin({minimize: true, sourceMap: true}));
	}

	config.plugins.push(new ExtractTextPlugin(libraryName + '.css'));

	config.module.rules.push({
		test: /\.scss$/,
		use: ExtractTextPlugin.extract({
			fallback: "style-loader",
			use: ['css-loader', 'sass-loader']
		}),
		exclude: /node_modules/
	});

	config.entry = [path.resolve(src, main)];

	outputFile = libraryName + '.js';
}
config.output.filename = outputFile;

config.module.rules.push({ test: /\.js?$/, loader: 'eslint-loader', exclude: /node_modules/ });
module.exports = config;