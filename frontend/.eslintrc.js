// http://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parser: 'babel-eslint',
  parserOptions: {
    sourceType: 'module'
  },
  env: {
    browser: true,
  },
  // https://github.com/feross/standard/blob/master/RULES.md#javascript-standard-style
  extends: 'standard',
  // required to lint *.vue files
  plugins: [
    'html'
  ],
  // add your custom rules here
  'rules': {
    // allow paren-less arrow functions
    'arrow-parens': 0,
    // allow async-await
    'generator-star-spacing': 0,
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0,

    // custom rules
    'comma-dangle': 0,
    'comma-spacing': 0,
    'indent': 'warn',
    'quotes': 'warn',
    'semi': 0,
    'no-unused-vars': 'warn',
    'camelcase': 0,
  },
  // globally enabled plugins
  'globals': {
    '_': true,
    "axios": true,
    "d3": true,
    'fjs': true,
    'Highcharts': true,
    'Immutable': true,
    'Mustache': true,
    'nunjucks': true,
  }
}
