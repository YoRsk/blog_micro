'use client';
import React, { useState, useEffect } from 'react';
import Link from 'next/link';

const RightSideList = () => {
    const TYPE_API_URL = `${process.env.NEXT_PUBLIC_BLOG_SERVICE_URL}/typesIndex`;
    const TAG_API_URL = `${process.env.NEXT_PUBLIC_BLOG_SERVICE_URL}/tagsIndex`;
    const [types, setTypes] = useState([]);
    const [tags, setTags] = useState([]);
    const [recommendBlogs, setRecommendBlogs] = useState([]);
  
    useEffect(() => {
      const fetchData = async () => {
        const typeResponse = await fetch(TYPE_API_URL);
        const typeData = await typeResponse.json();
        setTypes(typeData);
  
        const tagResponse = await fetch(TAG_API_URL);
        const tagData = await tagResponse.json();
        setTags(tagData);
  
        const recommendBlogResponse = await fetch('/api/recommendBlogs');
        const recommendBlogData = await recommendBlogResponse.json();
        setRecommendBlogs(recommendBlogData);
      };
  
      fetchData();
    }, []);
  
    return (
        <div className="five wide column">
        {/* 分类部分 */}
        <div className="ui segments">
          <div className="ui secondary segment">
            <div className="ui two column grid">
              <div className="column">
                <i className="idea icon"></i>分类
              </div>
              <div className="right aligned column">
                <Link href="/types" passHref>
                  more <i className="angle double right icon"></i>
                </Link>
              </div>
            </div>
          </div>
          <div className="ui teal segment">
            <div className="ui fluid vertical menu">
              {types.map(type => (
                <Link key={type.id} href={`/types/${type.id}`} passHref>
                  <div className="item">
                    {type.name}
                    <div className="ui teal basic left pointing label">
                      {type.blogCount}
                    </div>
                  </div>
                </Link>
              ))}
            </div>
          </div>
        </div>
      
        {/* 标签 */}
      <div className="ui segments m-margin-top-large">
        <div className="ui secondary segment">
          <div className="ui two column grid">
            <div className="column">
              <i className="tags icon"></i>标签
            </div>
            <div className="right aligned column">
              {/* 直接使用 Link 组件，移除嵌套的 <a> */}
              <Link href="/tags">
                more <i className="angle double right icon"></i>
              </Link>
            </div>
          </div>
        </div>
        <div className="ui teal segment">
          {tags.map(tag => (
            // Link 组件内部不再嵌套 <a>，而是直接包裹内容
            <Link key={tag.id} href={`/tags/${tag.id}`} passHref>
              <div className="ui teal basic left pointing label m-margin-tb-tiny">
                {tag.name}
                <div className="detail">{tag.blogCount}</div>
              </div>
            </Link>
          ))}
        </div>
      </div>

      {/* 最新推荐 */}
      <div className="ui segments m-margin-top-large">
        <div className="ui secondary segment">
          <i className="bookmark icon"></i>最新推荐
        </div>
        {recommendBlogs.map(blog => (
          <div key={blog.id} className="ui segment">
            {/* 同样，移除嵌套的 <a> 标签 */}
            <Link href={`/blog/${blog.id}`} passHref>
              <div className="m-black m-text-thin">{blog.title}</div>
            </Link>
          </div>
        ))}
      </div>
    </div>
    );
};

export default RightSideList;
