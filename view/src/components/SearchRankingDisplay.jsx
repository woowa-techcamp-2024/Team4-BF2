import React, { useState, useEffect } from 'react';
import { TrendingUp, Clock } from 'lucide-react';
import PropTypes from 'prop-types';

const SearchRankingDisplay = ({ onKeywordClick }) => {
  const [searchRankings, setSearchRankings] = useState([]);
  const [currentDateTime, setCurrentDateTime] = useState('');

  useEffect(() => {
    fetchSearchRankings();
    updateDateTime();
    const timer = setInterval(updateDateTime, 60000); // 1분마다 업데이트
    return () => clearInterval(timer);
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

  const updateDateTime = () => {
    const now = new Date();
    const month = now.getMonth() + 1;
    const day = now.getDate();
    const hour = now.getHours();
    const minute = now.getMinutes();

    setCurrentDateTime(`${month}월 ${day}일 ${hour}시 ${minute}분 기준`);
  };

  const getColorClass = (rank) => {
    if (rank === 1) return 'text-red-500';
    if (rank === 2) return 'text-orange-500';
    if (rank === 3) return 'text-yellow-500';
    return 'text-gray-500';
  };

  return (
      <div className="bg-white rounded-lg shadow-md p-4 mb-4">
        <div className="flex items-center justify-between mb-2">
          <h3 className="text-sm font-semibold flex items-center">
            <TrendingUp size={20} className="mr-2 text-teal-500" />
            인기 검색어 순위
          </h3>
          <div className="text-xs text-gray-500 flex items-center">
            <Clock size={12} className="mr-1" />
            {currentDateTime}
          </div>
        </div>
        <ul className="grid grid-cols-2 gap-2">
          {searchRankings.map((item) => (
              <li
                  key={item.rank}
                  className="flex items-center cursor-pointer hover:bg-gray-100 p-1 rounded"
                  onClick={() => onKeywordClick(item.keyword)}
              >
                <span className={`mr-2 text-sm font-medium ${getColorClass(item.rank)}`}>{item.rank}</span>
                <span className="text-sm">{item.keyword}</span>
              </li>
          ))}
        </ul>
      </div>
  );
};

SearchRankingDisplay.propTypes = {
  onKeywordClick: PropTypes.func.isRequired,
};

export default SearchRankingDisplay;