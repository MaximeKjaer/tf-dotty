/**
 * Copyright (c) 2017-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

// See https://docusaurus.io/docs/site-config for all the possible
// site configuration options.

const docSections = require("./sidebars.json").docs;
const firstDoc = Object.values(docSections)[0][0];

const siteConfig = {
  title: "tf-dotty",
  tagline: "Shape-safe TensorFlow in Dotty",
  url: "https://maximekjaer.github.io",
  baseUrl: "/tf-dotty/",
  projectName: "tf-dotty",
  organizationName: "MaximeKjaer",
  headerLinks: [
    { doc: firstDoc, label: "Docs" },
    { blog: true, label: "Blog" }
  ],
  docSections,
  firstDoc,
  // headerIcon: '',
  // footerIcon: '',
  favicon: "img/favicon.ico",

  /* Colors for website */
  colors: {
    primaryColor: "#de3423",
    secondaryColor: "#380d09"
  },

  /* Custom fonts for website */
  /*
  fonts: {
    myFont: [
      "Times New Roman",
      "Serif"
    ],
    myOtherFont: [
      "-apple-system",
      "system-ui"
    ]
  },
  */

  highlight: {
    // Highlight.js theme to use for syntax highlighting in code blocks.
    theme: "default"
  },

  // Add custom scripts here that would be placed in <script> tags.
  scripts: [
    "https://buttons.github.io/buttons.js",
    // MathJax
    "https://polyfill.io/v3/polyfill.min.js?features=es6",
    "https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"
  ],

  // On page navigation for the current documentation page.
  onPageNav: "separate",
  // No .html extensions for paths.
  cleanUrl: true,

  // For sites with a sizable amount of content, set collapsible to true.
  // Expand/collapse the links and subcategories under categories.
  // docsSideNavCollapsible: true,

  // Show documentation's last contributor's name.
  // enableUpdateBy: true,

  // Show documentation's last update time.
  // enableUpdateTime: true,

  repoUrl: "https://github.com/MaximeKjaer/tf-dotty"
};

module.exports = siteConfig;
