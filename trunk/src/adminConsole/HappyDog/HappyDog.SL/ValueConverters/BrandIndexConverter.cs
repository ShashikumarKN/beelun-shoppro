﻿using System;
using System.Globalization;
using System.Windows.Data;
using System.Windows;
using HappyDog.SL.Beelun.Shoppro.WSEntryManager;
using System.Collections.Generic;
using HappyDog.SL.Data;
using HappyDog.SL.ViewModels;

namespace HappyDog.SL.ValueConverters
{
    /// <summary>
    /// Convert type: (HappyDog.SL.Beelun.Shoppro.WebService.ItemManager.brand) brand <-->(string) name of this brand in brand list
    /// </summary>
    public class BrandIndexConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if (value == null)
            {
                return null;
            }
            else
            {
                return ((brand)value).name;
            }

        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if (value != null)
            {
                foreach (brand theBrand in ModelProvider.Instance.MDC.allBrands)
                {
                    if (theBrand.name == (string)value)
                    {
                        return (theBrand);
                    }
                }
                return null;
            }
            else
            {
                return null;
            }
        }
    }
}
