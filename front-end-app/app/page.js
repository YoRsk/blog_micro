import Head from 'next/head';
import Image from 'next/image';
import Link from 'next/link';
import 'semantic-ui-css/semantic.min.css'
import { Button } from 'semantic-ui-react';
import BlogList from '../components/BlogList'; 
// function MyComponent() {
//   return <Button>Click Me</Button>;
// }
export default function Home() {


  return (
    <>
      <Head>
        <title>Blog of Liuyi</title>
        <meta charset="UTF-8" />
        <meta name="description" content="Welcome to my blog" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      </Head>

      {/* Navigation */}
      <nav className="ui inverted attached segment m-padded-tb-mini m-shadow-small">
        {/* Navigation content or components */}
      </nav>

      {/* Middle Content */}
      <div className="m-container m-padded-tb-big animated fadeIn">
        <div className="ui container">
          <div className="ui stackable grid">
            
            {/* Left Blog List */}
            <BlogList />

            {/* Right Side Top Content */}
            <div className="five wide column">
              {/* Additional components like Categories, Tags, etc. */}
            </div>
          </div>
        </div>
      </div>

      <br />
      <br />

      {/* Footer */}
      <footer className="ui inverted vertical segment m-padded-tb-massive">
        {/* Footer content or components */}
      </footer>

      {/* Semantic UI and jQuery scripts */}
      {/* Consider removing jQuery and using React alternatives */}
    </>
  );
}
