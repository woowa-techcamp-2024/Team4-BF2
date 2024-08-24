import React, { useState } from 'react';
import { X } from 'lucide-react';

const RegisterRestaurant = ({ isOpen, onClose }) => {
  const [step, setStep] = useState(1);
  const [restaurantData, setRestaurantData] = useState({
    name: '',
    phone: '',
    address: '',
    introduction: '',
    image: '',
    operatingTime: '',
    closedDays: '',
    minimumOrderAmount: '',
    deliveryLocation: ''
  });
  const [categoryData, setCategoryData] = useState({ name: '', description: '' });
  const [menuData, setMenuData] = useState({ name: '', description: '', price: '' });
  const [restaurantId, setRestaurantId] = useState('');
  const [categoryId, setCategoryId] = useState('');

  const handleRestaurantChange = (e) => {
    setRestaurantData({ ...restaurantData, [e.target.name]: e.target.value });
  };

  const handleCategoryChange = (e) => {
    setCategoryData({ ...categoryData, [e.target.name]: e.target.value });
  };

  const handleMenuChange = (e) => {
    setMenuData({ ...menuData, [e.target.name]: e.target.value });
  };

  const registerRestaurant = async () => {
    try {
      const response = await fetch('/api/v1/restaurants', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(restaurantData)
      });
      const data = await response.json();
      if (data.success) {
        setRestaurantId(data.response.restaurantId);
        setStep(2);
      } else {
        alert('가게 등록에 실패했습니다.');
      }
    } catch (error) {
      console.error('Error registering restaurant:', error);
      alert('가게 등록 중 오류가 발생했습니다.');
    }
  };

  const registerCategory = async () => {
    try {
      const response = await fetch(`/api/v1/menus/categories/${restaurantId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(categoryData)
      });
      const data = await response.json();
      if (data.success) {
        setCategoryId(data.response.menuCategoryUuid);
        setStep(3);
      } else {
        alert('메뉴 카테고리 등록에 실패했습니다.');
      }
    } catch (error) {
      console.error('Error registering category:', error);
      alert('메뉴 카테고리 등록 중 오류가 발생했습니다.');
    }
  };

  const registerMenu = async () => {
    try {
      const response = await fetch(`/api/v1/menus/${categoryId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(menuData)
      });
      const data = await response.json();
      if (data.success) {
        alert('메뉴가 성공적으로 등록되었습니다.');
        onClose();
      } else {
        alert('메뉴 등록에 실패했습니다.');
      }
    } catch (error) {
      console.error('Error registering menu:', error);
      alert('메뉴 등록 중 오류가 발생했습니다.');
    }
  };

  if (!isOpen) return null;

  return (
      <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div className="bg-white p-8 rounded-lg max-w-md w-full">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-xl font-bold">
              {step === 1 ? '가게 등록' : step === 2 ? '메뉴 카테고리 등록' : '메뉴 등록'}
            </h2>
            <button onClick={onClose} className="text-gray-500 hover:text-gray-700">
              <X size={24} />
            </button>
          </div>

          {step === 1 && (
              <form onSubmit={(e) => { e.preventDefault(); registerRestaurant(); }}>
                <input
                    type="text"
                    name="name"
                    value={restaurantData.name}
                    onChange={handleRestaurantChange}
                    placeholder="가게 이름"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <input
                    type="tel"
                    name="phone"
                    value={restaurantData.phone}
                    onChange={handleRestaurantChange}
                    placeholder="전화번호"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <input
                    type="text"
                    name="address"
                    value={restaurantData.address}
                    onChange={handleRestaurantChange}
                    placeholder="주소"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <textarea
                    name="introduction"
                    value={restaurantData.introduction}
                    onChange={handleRestaurantChange}
                    placeholder="가게 소개"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <input
                    type="text"
                    name="image"
                    value={restaurantData.image}
                    onChange={handleRestaurantChange}
                    placeholder="이미지 URL"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <input
                    type="text"
                    name="operatingTime"
                    value={restaurantData.operatingTime}
                    onChange={handleRestaurantChange}
                    placeholder="영업 시간"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <input
                    type="text"
                    name="closedDays"
                    value={restaurantData.closedDays}
                    onChange={handleRestaurantChange}
                    placeholder="휴무일"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <input
                    type="number"
                    name="minimumOrderAmount"
                    value={restaurantData.minimumOrderAmount}
                    onChange={handleRestaurantChange}
                    placeholder="최소 주문 금액"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <input
                    type="text"
                    name="deliveryLocation"
                    value={restaurantData.deliveryLocation}
                    onChange={handleRestaurantChange}
                    placeholder="배달 지역"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <button type="submit" className="w-full bg-teal-500 text-white p-2 rounded hover:bg-teal-600">
                  가게 등록하기
                </button>
              </form>
          )}

          {step === 2 && (
              <form onSubmit={(e) => { e.preventDefault(); registerCategory(); }}>
                <input
                    type="text"
                    name="name"
                    value={categoryData.name}
                    onChange={handleCategoryChange}
                    placeholder="메뉴 카테고리 이름"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <textarea
                    name="description"
                    value={categoryData.description}
                    onChange={handleCategoryChange}
                    placeholder="메뉴 카테고리 설명"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <button type="submit" className="w-full bg-teal-500 text-white p-2 rounded hover:bg-teal-600">
                  카테고리 등록하기
                </button>
              </form>
          )}

          {step === 3 && (
              <form onSubmit={(e) => { e.preventDefault(); registerMenu(); }}>
                <input
                    type="text"
                    name="name"
                    value={menuData.name}
                    onChange={handleMenuChange}
                    placeholder="메뉴 이름"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <textarea
                    name="description"
                    value={menuData.description}
                    onChange={handleMenuChange}
                    placeholder="메뉴 설명"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <input
                    type="number"
                    name="price"
                    value={menuData.price}
                    onChange={handleMenuChange}
                    placeholder="가격"
                    className="w-full mb-2 p-2 border rounded"
                    required
                />
                <button type="submit" className="w-full bg-teal-500 text-white p-2 rounded hover:bg-teal-600">
                  메뉴 등록하기
                </button>
              </form>
          )}
        </div>
      </div>
  );
};

export default RegisterRestaurant;