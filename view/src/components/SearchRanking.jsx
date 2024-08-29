import React, { useState, useEffect, useCallback } from 'react';
import { TrendingUp } from 'lucide-react';

const SearchRanking = ({ onSearch, keyword, setKeyword }) => {
  const [searchRankings, setSearchRankings] = useState([]);
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => {
    fetchSearchRankings();
  }, []);

  const fetchSearchRankings = async () => {
    try {
      const response = await fetch('/api/v1/ranking');
      const data = await response.json();
      setSearchRankings(data);
    } catch (error) {
      console.error('Error fetching search rankings:', error);
    }
  };

  const handleInputChange = useCallback((e) => {
    setKeyword(e.target.value);
  }, [setKeyword]);

  const handleSubmit = useCallback((e) => {
    e.preventDefault();
    if (keyword.trim()) {
      onSearch(keyword);
    }
    setIsVisible(false);
  }, [keyword, onSearch]);

  const handleKeywordSelect = useCallback((selectedKeyword) => {
    setKeyword(selectedKeyword);
    onSearch(selectedKeyword);
    setIsVisible(false);
  }, [onSearch, setKeyword]);

  return (
      <form onSubmit={handleSubmit} className="relative">
        <input
            type="text"
            placeholder="검색어 입력"
            value={keyword}
            onChange={handleInputChange}
            onFocus={() => setIsVisible(true)}
            onBlur={() => setTimeout(() => setIsVisible(false), 200)}
            className="w-full py-2 pl-10 pr-4 border rounded-full bg-gray-100 border-teal-400 focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-teal-500"
        />
        {isVisible && searchRankings.length > 0 && (
            <div className="absolute top-full left-0 right-0 bg-white border border-gray-200 rounded-lg shadow-lg mt-1 z-20">
              <div className="p-2 flex items-center justify-between border-b">
                <span className="text-sm font-medium">인기 검색어</span>
                <TrendingUp size={16} className="text-teal-500" />
              </div>
              <ul className="py-2">
                {searchRankings.map((item) => (
                    <li
                        key={item.rank}
                        className="px-4 py-2 hover:bg-gray-100 cursor-pointer flex items-center"
                        onClick={() => handleKeywordSelect(item.keyword)}
                    >
                      <span className="mr-2 text-sm font-medium text-teal-500">{item.rank}</span>
                      <span className="text-sm">{item.keyword}</span>
                    </li>
                ))}
              </ul>
            </div>
        )}
      </form>
  );
};

export default SearchRanking;