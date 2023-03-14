/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PictureComponent from '@/entities/picture/picture.vue';
import PictureClass from '@/entities/picture/picture.component';
import PictureService from '@/entities/picture/picture.service';
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
  describe('Picture Management Component', () => {
    let wrapper: Wrapper<PictureClass>;
    let comp: PictureClass;
    let pictureServiceStub: SinonStubbedInstance<PictureService>;

    beforeEach(() => {
      pictureServiceStub = sinon.createStubInstance<PictureService>(PictureService);
      pictureServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PictureClass>(PictureComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          pictureService: () => pictureServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      pictureServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPictures();
      await comp.$nextTick();

      // THEN
      expect(pictureServiceStub.retrieve.called).toBeTruthy();
      expect(comp.pictures[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      pictureServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(pictureServiceStub.retrieve.callCount).toEqual(1);

      comp.removePicture();
      await comp.$nextTick();

      // THEN
      expect(pictureServiceStub.delete.called).toBeTruthy();
      expect(pictureServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
