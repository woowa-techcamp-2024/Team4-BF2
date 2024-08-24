import React, { useEffect, useRef, useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { Search, MapPin, ChevronLeft, ChevronRight } from 'lucide-react';
import { koreaDistricts } from '../data/koreaDistricts';

const flattenAddressData = (data) => {
  return data.reduce((acc, province) => {
    province.cities.forEach(city => {
      city.districts.forEach(district => {
        acc.push(`${province.name} ${city.name} ${district}`);
      });
    });
    return acc;
  }, []);
};

const flattenedAddresses = flattenAddressData(koreaDistricts);

const LocationSelectionPage = ({ onSelectLocation, currentLocation, onClose }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredAddresses, setFilteredAddresses] = useState([]);

  useEffect(() => {
    const lowercaseSearchTerm = searchTerm.toLowerCase();
    if (lowercaseSearchTerm.length > 1) {
      const filtered = flattenedAddresses.filter(address =>
          address.toLowerCase().includes(lowercaseSearchTerm)
      );
      setFilteredAddresses(filtered.slice(0, 100)); // Limit to 100 results for performance
    } else {
      setFilteredAddresses([]);
    }
  }, [searchTerm]);

  const handleAddressSelect = (address) => {
    onSelectLocation(address);
    onClose();
  };

  return (
      <div className="flex flex-col h-full bg-white">
        <div className="sticky top-0 bg-white p-4 border-b z-10 ">
          <div className="flex items-center mb-4 ">
            <button onClick={onClose} className="mr-4">
              <ChevronLeft size={24} />
            </button>
            <h2 className="text-lg font-bold">주소 검색</h2>
          </div>
          <div className="relative">
            <input
                type="text"
                placeholder="핒 칰 컾 곝 슽"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="w-full p-3 pl-10 border rounded-lg
                hover:outline-none hover:ring-2 hover:ring-teal-500 hover:border-teal-500
                focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-teal-500"
            />
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
          </div>
        </div>
        <ul className="flex-grow overflow-y-auto p-4">
          {filteredAddresses.map((address, index) => (
              <li
                  key={index}
                  className="py-3 border-b last:border-b-0 flex items-center justify-between cursor-pointer hover:bg-gray-100 "
                  onClick={() => handleAddressSelect(address)}
              >
                <span>{address.replace(/_/g, ' ')}</span>
                <ChevronRight size={20} />
              </li>
          ))}
        </ul>
      </div>
  );
};

const Modal = ({ isOpen, onClose, children }) => {
  const modalRef = useRef(null);

  useEffect(() => {
    const handleEscape = (event) => {
      if (event.key === 'Escape') {
        onClose();
      }
    };

    if (isOpen) {
      document.addEventListener('keydown', handleEscape);
      modalRef.current?.focus();
      document.body.style.overflow = 'hidden';
    }

    return () => {
      document.removeEventListener('keydown', handleEscape);
      document.body.style.overflow = 'visible';
    };
  }, [isOpen, onClose]);

  if (!isOpen) return null;

  return (
      <AnimatePresence>
        {isOpen && (
            <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                className="fixed inset-0 z-50 overflow-hidden"
                aria-labelledby="modal-title"
                role="dialog"
                aria-modal="true"
            >
              <div className="flex items-start justify-center min-h-screen">
                <motion.div
                    initial={{ opacity: 0 }}
                    animate={{ opacity: 0.75 }}
                    exit={{ opacity: 0 }}
                    className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"
                    aria-hidden="true"
                    onClick={onClose}
                ></motion.div>

                <motion.div
                    ref={modalRef}
                    initial={{ opacity: 0, y: "-100%" }}
                    animate={{ opacity: 1, y: 0 }}
                    exit={{ opacity: 0, y: "-100%" }}
                    transition={{ type: "spring", damping: 25, stiffness: 500 }}
                    className="relative bg-white w-full h-full overflow-hidden shadow-xl"
                    tabIndex={-1}
                >
                  {children}
                </motion.div>
              </div>
            </motion.div>
        )}
      </AnimatePresence>
  );
};

const LocationModal = ({ isOpen, onClose, onSelectLocation, currentLocation }) => {
  return (
      <Modal isOpen={isOpen} onClose={onClose}>
        <LocationSelectionPage
            onSelectLocation={(location) => {
              onSelectLocation(location);
              onClose();
            }}
            currentLocation={currentLocation}
            onClose={onClose}
        />
      </Modal>
  );
};

export default LocationModal;