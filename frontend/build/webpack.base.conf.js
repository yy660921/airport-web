const path = require('path')
const webpack = require('webpack')
const glob = require('glob')
const utils = require('./utils')
const config = require('../config')
const vueLoaderConfig = require('./vue-loader.conf')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const CommonsChunkPlugin = require('webpack/lib/optimize/CommonsChunkPlugin')

function resolve (dir) {
  return path.join(__dirname, '..', dir)
}

const entries = {};
const chunks = [];
glob.sync('./src/modules/**/main.js').forEach(filepath => {
  const pathes = filepath.split('./src/modules/')[1].split('/main.js');
  const chunk = pathes.length === 1 ? 'main' : pathes[0];
  entries[chunk] = filepath;
  chunks.push(chunk);
});

const application = {
  entry: entries,
  output: {
    path: config.build.assetsRoot,
    filename: 'static/js/[name].js',
    publicPath: process.env.NODE_ENV === 'production'
      ? config.build.assetsPublicPath
      : config.dev.assetsPublicPath
  },
  resolve: {
    extensions: ['.js', '.vue', '.json'],
    alias: {
      'vue$': 'vue/dist/vue.esm.js',
      '@': resolve('src'),
      'src': path.resolve(__dirname, '../src'),
      'assets': path.resolve(__dirname, '../src/assets'),
      'modules': path.resolve(__dirname, '../src/modules'),
      'components': path.resolve(__dirname, '../src/components'),
    },
    modules: [path.join(__dirname, '../node_modules')]
  },
  module: {
    rules: [
      {
        test: /\.(js|vue)$/,
        loader: 'eslint-loader',
        enforce: 'pre',
        include: [
          resolve('src'),
          resolve('test'),
          resolve('node_modules/vue-echarts-v3'),
        ],
        options: {
          formatter: require('eslint-friendly-formatter')
        }
      },
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: vueLoaderConfig
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
        include: [
          resolve('src'),
          resolve('test'),
          resolve('node_modules/ansi-regex'),
          resolve('node_modules/_ansi-regex'), // for cnpm
          resolve('node_modules/strip-ansi'),
          resolve('node_modules/_strip-ansi'), // for cnpm
	        resolve('node_modules/vue-echarts-v3'),
          resolve('node_modules/_vue-echarts-v3'), // for cnpm
	      ],
      },
      {
        test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('img/[name].[hash:7].[ext]')
        }
      },
      {
        test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('fonts/[name].[hash:7].[ext]')
        }
      }
    ]
  },
  'plugins': [
    new CommonsChunkPlugin({
      name: 'vendor',
      chunks: chunks,
      minChunks: 2
    }),
    // FIXME
    // extract webpack runtime and module manifest to its own file in order to
    // prevent vendor hash from being updated whenever app bundle is updated
    // new CommonsChunkPlugin({
    //   name: 'manifest',
    //   chunks: ['vendor'],
    //   minChunks: 1,
    // }),
    // extra dependencies.
    new webpack.ProvidePlugin({
      _: 'lodash',
    }),
    new webpack.ProvidePlugin({
      axios: 'axios',
    }),
    new webpack.ProvidePlugin({
      d3: 'd3',
    }),
    new webpack.ProvidePlugin({
      Highcharts: 'highcharts',
    }),
    new webpack.ProvidePlugin({
      Immutable: 'immutable',
    }),
    new webpack.ProvidePlugin({
      Mustache: 'mustache',
    }),
    new webpack.ProvidePlugin({
      nunjucks: 'nunjucks',
    }),
  ]
};

glob.sync('./src/modules/**/index.html').forEach(filepath => {
  const pathes = filepath.split('./src/modules/')[1].split('/index.html');
  const chunk = pathes.length === 1 ? 'index' : pathes[0];
  application.plugins.push(new HtmlWebpackPlugin({
    filename: chunk === 'index' ? 'index.html' : chunk + '/index.html',
    template: filepath,
    inject: true,
    favicon: './favicon.ico',
    minify: {
      // more options:
      // https://github.com/kangax/html-minifier#options-quick-reference
      removeComments: true,
      collapseWhitespace: true,
      removeAttributeQuotes: true
    },
    cache: true,
    hash: true,
    chunks: ['manifest', 'vendor', chunk === 'index' ? 'main' : chunk],
    chunksSortMode: 'dependency'
  }))
});

module.exports = application;
