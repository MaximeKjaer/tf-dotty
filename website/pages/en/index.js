/**
 * Copyright (c) 2017-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

const React = require("react");
const CompLibrary = require("../../core/CompLibrary.js");
const Container = CompLibrary.Container;
const GridBlock = CompLibrary.GridBlock;

class HomeSplash extends React.Component {
  render() {
    const { siteConfig, language = "" } = this.props;
    const { baseUrl, docsUrl } = siteConfig;
    const docsPart = `${docsUrl ? `${docsUrl}/` : ""}`;
    const langPart = `${language ? `${language}/` : ""}`;
    const docUrl = doc => `${baseUrl}${docsPart}${langPart}${doc}`;

    const SplashContainer = props => (
      <div className="homeContainer">
        <div className="homeSplashFade">
          <div className="wrapper homeWrapper">{props.children}</div>
        </div>
      </div>
    );

    const Logo = props => (
      <div className="projectLogo">
        <img src={props.img_src} alt="Project Logo" />
      </div>
    );

    const ProjectTitle = () => (
      <h2 className="projectTitle">
        {siteConfig.title}
        <small>{siteConfig.tagline}</small>
      </h2>
    );

    const PromoSection = props => (
      <div className="section promoSection">
        <div className="promoRow">
          <div className="pluginRowBlock">{props.children}</div>
        </div>
      </div>
    );

    const Button = props => (
      <div className="pluginWrapper buttonWrapper">
        <a className="button" href={props.href} target={props.target}>
          {props.children}
        </a>
      </div>
    );

    return (
      <SplashContainer>
        <div className="inner">
          <ProjectTitle siteConfig={siteConfig} />
          <PromoSection>
            <Button href={docUrl("getting-started.html")}>Get started</Button>
          </PromoSection>
        </div>
      </SplashContainer>
    );
  }
}


const Block = props => (
    <Container
        padding={["bottom", "top"]}
        id={props.id}
        background={props.background}
    >
      <GridBlock align="left" contents={props.children} layout={props.layout} />
    </Container>
);

const Features = props => {
  const imageUrl = filename => `${props.siteConfig.baseUrl}img/${filename}`
  const features = [
    {
      title: "Compile-time errors for `tf.reshape`",
      content: "When reshaping a tensor, the output tensor must have the same number of elements as the input tensor. " +
          "tf-dotty can check that this is the case at compile-time",
      image: imageUrl("reshape-error.jpg"),
      imageAlt: "A reshape error being underlined in the editor by tf-dotty",
      imageAlign: "left"
    },
    {
      title: "Compile-time errors for reduction operations",
      content: "TensorFlow has a series of reduction operations, like " +
          "[`tf.cumprod`](https://www.tensorflow.org/versions/r1.15/api_docs/python/tf/math/cumprod), " +
          "[`tf.reduce_mean`](https://www.tensorflow.org/versions/r1.15/api_docs/python/tf/math/reduce_mean) or " +
          "[`tf.reduce_variance`](https://www.tensorflow.org/versions/r1.15/api_docs/python/tf/math/reduce_variance), " +
          'with which you can reduce *along an axis*. However, it is often confusing what the resulting tensor shape will be; ' +
          "tf-dotty infers the resulting shape at compile-time, and reports an error if it does not conform to the expected shape",
      image: imageUrl("reduce-error.jpg"),
      imageAlt: "A reduction error being underlined in the editor by tf-dotty",
      imageAlign: "right"
    }
  ];
  return (
      <div
          className="productShowcaseSection paddingBottom"
          style={{ textAlign: "left" }}
      >
        {features.map(feature => (
            <Block key={feature.title}>{[feature]}</Block>
        ))}
      </div>
  );
};

class Index extends React.Component {
  render() {
    const { config: siteConfig, language = "" } = this.props;

    return (
      <div>
        <HomeSplash siteConfig={siteConfig} language={language} />
        <Features siteConfig={siteConfig}/>
      </div>
    );
  }
}

module.exports = Index;
