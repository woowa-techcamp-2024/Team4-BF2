import React, {useCallback, useEffect, useRef, useState} from 'react';
import {ChevronLeft, MapPin, Search, ShoppingCart, Star} from 'lucide-react';
import LocationModal from './LocationModal';
import RegisterRestaurant from './RegisterRestaurant';
import SearchRanking from './SearchRanking';
import SearchRankingDisplay from './SearchRankingDisplay'

const foodImages = [
  'img1.jpg',
  'img2.jpg',
  'img3.jpg',
  'img4.jpg',
  'img5.jpg',
  'img6.jpg',
  'img7.jpg',
  'img8.jpg',
  'img9.jpg',
];

const getRandomFoodImage = () => {
  const randomIndex = Math.floor(Math.random() * foodImages.length);
  return `/img/${foodImages[randomIndex]}`;
};

const RestaurantList = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [activeTab, setActiveTab] = useState('인기 검색어');
  const [keyword, setKeyword] = useState('치킨');
  const [isLoading, setIsLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const [deliveryLocation, setDeliveryLocation] = useState('서울특별시 송파구 방이동');
  const [isLocationModalOpen, setIsLocationModalOpen] = useState(false);
  const [isRegisterModalOpen, setIsRegisterModalOpen] = useState(false);
  const [pageNumber, setPageNumber] = useState(0);
  const [searchTerm, setSearchTerm] = useState('');

  const observer = useRef();

  const fetchData = useCallback(async (searchKeyword, page = 0, isNewSearch = false) => {
    setIsLoading(true);
    try {
      const formattedDeliveryLocation = deliveryLocation.replace(/\s+/g, '_');
      console.log(`Fetching data for keyword: ${searchKeyword}, page: ${page}, location: ${formattedDeliveryLocation}`);
      const response = await fetch(
          `/api/v1/restaurant-summary/cache?keyword=${searchKeyword}&deliveryLocation=${formattedDeliveryLocation}&pageNumber=${page}`
      );
      const data = await response.json();
      if (data.success && Array.isArray(data.response)) {
        const restaurantsWithImages = data.response.map(restaurant => ({
          ...restaurant,
          foodImage: getRandomFoodImage()
        }));
        setRestaurants(prev => isNewSearch ? restaurantsWithImages : [...prev, ...restaurantsWithImages]);
        setHasMore(data.response.length > 0);
        setPageNumber(page);
      } else {
        console.error('Invalid data format:', data);
        setHasMore(false);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
      setHasMore(false);
    }
    setIsLoading(false);
  }, [deliveryLocation]);

  useCallback((event) => {
    event.preventDefault();
    setRestaurants([]); // Reset restaurants list
    setHasMore(true);
    setPageNumber(0);
    fetchData(keyword, 0, true);
  }, [fetchData, keyword]);

  const loadMore = useCallback(() => {
    if (!isLoading && hasMore) {
      fetchData(keyword, pageNumber + 1, false);
    }
  }, [isLoading, hasMore, fetchData, keyword, pageNumber]);

  const lastRestaurantElementRef = useCallback(node => {
    if (isLoading) return;
    if (observer.current) observer.current.disconnect();
    observer.current = new IntersectionObserver(entries => {
      if (entries[0].isIntersecting && hasMore) {
        loadMore();
      }
    });
    if (node) observer.current.observe(node);
  }, [isLoading, hasMore, loadMore]);

  const handleKeywordChange = useCallback((event) => {
    setKeyword(event.target.value);
  }, []);

  const handleSearch = useCallback((searchKeyword) => {
    setSearchTerm(searchKeyword);
    setRestaurants([]);
    setHasMore(true);
    setPageNumber(0);
    fetchData(searchKeyword, 0, true);
    setActiveTab('전체'); // Switch back to '전체' tab after search
  }, [fetchData]);

  const handleKeywordSelect = useCallback((selectedKeyword) => {
    setKeyword(selectedKeyword);
    handleSearch(selectedKeyword);
  }, [handleSearch]);

  useCallback((selectedKeyword) => {
    if (selectedKeyword) {
      setKeyword(selectedKeyword);
      setRestaurants([]);
      setHasMore(true);
      setPageNumber(0);
      fetchData(selectedKeyword, 0, true);
      setActiveTab('전체'); // Switch back to '전체' tab after search
    }
  }, [fetchData]);

  const handleLocationChange = (newLocation) => {
    setDeliveryLocation(newLocation);
    setRestaurants([]);
    setHasMore(true);
    setPageNumber(0);
    fetchData(keyword, 0, true);
  };

  useEffect(() => {
    if (searchTerm) {
      fetchData(searchTerm, 0, true);
    }
  }, [searchTerm, fetchData]);

  const tabs = ['전체', '인기 검색어', '배달', '가게 등록'];

  return (
      <div className="max-w-md mx-auto bg-gray-100 min-h-screen">
        <div className="sticky top-0 bg-white z-10">
          <div className="flex items-center p-4 border-b">
            <button className="mr-4">
              <ChevronLeft size={24}/>
            </button>
            <div className="flex-grow relative">
              <SearchRanking
                  onSearch={handleSearch}
                  keyword={keyword}
                  setKeyword={setKeyword}
              />
            </div>
            <button className="ml-4">
              <ShoppingCart size={24}/>
            </button>
          </div>

          <button
              onClick={() => setIsLocationModalOpen(true)}
              className="w-full py-3 px-4 bg-white border-b flex items-center justify-between
          hover:outline-none hover:ring-teal-500 hover:border-teal-500
          focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-teal-500"
          >
            <div className="flex items-center">
              <MapPin className="mr-2" size={20}/>
              <span className="font-medium">{deliveryLocation}</span>
            </div>
            <ChevronLeft className="transform rotate-180" size={20}/>
          </button>

          <div className="flex border-b overflow-x-auto">
            {tabs.map((tab) => (
                <button
                    key={tab}
                    className={`flex-shrink-0 py-3 px-4 text-sm 
              hover:outline-none hover:ring-teal-500 hover:border-teal-500
              focus:outline-none focus:ring-teal-500 focus:border-teal-500 ${
                        activeTab === tab
                            ? 'border-b-2 border-teal-400 font-bold' : ''
                    }`}
                    onClick={() => {
                      setActiveTab(tab);
                      if (tab === '가게 등록') {
                        setIsRegisterModalOpen(true);
                      }
                    }}
                >
                  {tab}
                </button>
            ))}
          </div>

          <div className="p-4 bg-white border-b">
            <p className="text-sm font-bold">
              기본순 <span className="text-xs text-gray-500">ⓘ</span>
            </p>
          </div>
        </div>

        <div className="p-4">
          {activeTab === '인기 검색어' ? (
              <SearchRankingDisplay onKeywordClick={handleKeywordSelect} />
          ) : (
              <>
          {restaurants.map((restaurant, index) => (
              <div
                  key={restaurant.restaurantUuid}
                  ref={index === restaurants.length - 1
                      ? lastRestaurantElementRef : null}
                  className="bg-white rounded-lg p-4 mb-4 shadow"
              >
                <div className="flex justify-between">
                  <div className="flex-grow pr-4 max-w-[calc(100%-5rem)]">
                    <div className="flex items-center mb-1">
                      <h3 className="font-semibold text-base truncate">{restaurant.restaurantName}</h3>
                      {restaurant.hasAdvertisement && (
                          <span
                              className="ml-2 text-xs font-medium text-gray-500 border border-gray-500 rounded px-1 flex-shrink-0">광고</span>
                      )}
                    </div>
                    <div className="flex items-center mb-1">
                      <Star
                          className="h-4 w-4 text-yellow-400 mr-1 flex-shrink-0"/>
                      <span className="text-sm font-medium">
                        {restaurant.rating.toFixed(1)}
                      </span>
                      <span className="text-xs text-gray-500 ml-1">
                        리뷰 {restaurant.reviewCount}
                      </span>
                    </div>
                    <p className="text-xs text-gray-500 mb-1">
                      {restaurant.min > 0
                          ? `${restaurant.min}-${restaurant.max}분` : ''}
                    </p>
                    <p className="text-xs text-teal-500 overflow-hidden text-ellipsis"
                       style={{
                         display: '-webkit-box',
                         WebkitLineClamp: 1,
                         WebkitBoxOrient: 'vertical'
                       }}>
                      {restaurant.menus}
                    </p>
                    <p className="text-xs text-gray-500 mb-1">
                      최소주문 {restaurant.minimumOrderAmount.toLocaleString()}원
                    </p>
                    {restaurant.hasCoupon && (
                        <div
                            className="mt-1 inline-block bg-blue-50 text-blue-700 text-xs font-medium px-2 py-1 rounded">
                          {restaurant.couponName}
                        </div>
                    )}
                  </div>
                  <div
                      className="w-20 h-20 bg-gray-200 rounded-lg flex-shrink-0 overflow-hidden">
                    {restaurant.foodImage && (
                        <img
                            src={restaurant.foodImage}
                            alt={restaurant.restaurantName}
                            className="w-full h-full object-cover"
                        />
                    )}
                  </div>
                </div>
              </div>
          ))}

          {isLoading && <p className="text-center py-4">로딩 중...</p>}
          {!isLoading && !hasMore && (
              <>
                <SearchRankingDisplay />
                <p className="text-center py-4">더 이상 표시할 식당이 없습니다.</p>
              </>
          )}
              </>
          )}
        </div>

        <div
            className="sticky bottom-0 bg-teal-400 text-white p-4 rounded-t-lg text-center">
          <p className="font-bold">배민 클럽으로 무료 배달</p>
          <p className="text-sm">지금 가입하세요!! ~~</p>
        </div>

        <LocationModal
            isOpen={isLocationModalOpen}
            onClose={() => setIsLocationModalOpen(false)}
            onSelectLocation={handleLocationChange}
            currentLocation={deliveryLocation}
        />

        <RegisterRestaurant
            isOpen={isRegisterModalOpen}
            onClose={() => setIsRegisterModalOpen(false)}
        />
      </div>
  );
};

export default RestaurantList;
