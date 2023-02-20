/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ItemComponent from '@/entities/item/item.vue';
import ItemClass from '@/entities/item/item.component';
import ItemService from '@/entities/item/item.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Item Management Component', () => {
    let wrapper: Wrapper<ItemClass>;
    let comp: ItemClass;
    let itemServiceStub: SinonStubbedInstance<ItemService>;

    beforeEach(() => {
      itemServiceStub = sinon.createStubInstance<ItemService>(ItemService);
      itemServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ItemClass>(ItemComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          itemService: () => itemServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      itemServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllItems();
      await comp.$nextTick();

      // THEN
      expect(itemServiceStub.retrieve.called).toBeTruthy();
      expect(comp.items[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      itemServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(itemServiceStub.retrieve.callCount).toEqual(1);

      comp.removeItem();
      await comp.$nextTick();

      // THEN
      expect(itemServiceStub.delete.called).toBeTruthy();
      expect(itemServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
