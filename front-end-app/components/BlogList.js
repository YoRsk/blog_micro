'use client';
import React, { useEffect, useState } from 'react';
import Image from 'next/image';
import Link from 'next/link';

const BlogList = () => {
    const BLOG_API_URL = 'http://localhost:8081/api/blogs';
    const [blogs, setBlogs] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchBlogs = async () => {
            setLoading(true);
            try {
                const response = await fetch(BLOG_API_URL);
                const data = await response.json();
                setBlogs(data.content); // Assuming the response follows the Page structure
            } catch (error) {
                console.error('Error fetching blogs', error);
            }
            setLoading(false);
        };

        fetchBlogs();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!blogs.length) {
        return <div>No blogs available.</div>;
    }

    return (
        <div className="eleven wide column">
            <div className="ui top attached segment">
                <div className="ui middle aligned two column grid">
                    <div className="column">
                        <h3 className="ui teal header">Blog</h3>
                    </div>
                    <div className="right aligned column">
                        <h2 className="ui orange header m-inline-block m-text-thin">{blogs.length}</h2> 篇
                    </div>
                </div>
            </div>

            <div className="ui attached segment">
                {blogs.map((blog) => (
                    <div key={blog.id} className="ui padded vertical segment m-padded-tb-large">
                        <div className="ui middle aligned mobile reversed stackable grid">
                            <div className="eleven wide column">
                                <h3 className="ui header">
                                    <Link href={`/blog/${blog.id}`}>{blog.title}</Link>
                                </h3>
                                <p className="m-text">{blog.description}</p>
                                <div className="ui grid">
                                    <div className="eleven wide column">
                                        <div className="ui mini horizontal link list">
                                            <div className="item">
                                                <Image 
                                                    src={blog.user && blog.user.avatar ? blog.user.avatar : '/default-avatar.png'} 
                                                    alt="User Avatar" 
                                                    width={100} 
                                                    height={100} 
                                                    className="ui avatar image" 
                                                />
                                                <div className="content">
                                                    <span className="header">{blog.user ? blog.user.nickname : 'Anonymous'}</span>
                                                </div>
                                            </div>
                                            <div className="item">
                                                <i className="calendar icon"></i>
                                                <span>{new Date(blog.updateTime).toLocaleDateString()}</span>
                                            </div>
                                            <div className="item">
                                                <i className="eye icon"></i>
                                                <span>{blog.views}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="right aligned five wide column">
                                        <span className="ui teal basic label m-padded-tiny m-text-thin">{blog.type.name}技术讨论</span>
                                    </div>
                                </div>
                            </div>
                            <div className="five wide column">
                                <Link href={`/blog/${blog.id}`}>
                                    <Image 
                                        src={blog.firstPicture ? blog.firstPicture : '/default-image.png'} 
                                        alt="Blog Image" 
                                        width={800} 
                                        height={450} 
                                        className="ui rounded image" 
                                    />
                                </Link>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            <div className="ui bottom attached segment">
                {/* Pagination logic and links */}
            </div>
        </div>
    );
};

export default BlogList;
